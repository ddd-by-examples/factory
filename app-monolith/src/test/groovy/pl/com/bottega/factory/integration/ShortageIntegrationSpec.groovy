package src.test.groovy.pl.com.bottega.factory.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import pl.com.bottega.factory.AppConfiguration
import pl.com.bottega.factory.ProductTrait
import pl.com.bottega.factory.demand.forecasting.Adjustment
import pl.com.bottega.factory.demand.forecasting.Demand
import pl.com.bottega.factory.demand.forecasting.command.DemandAdjustmentEntity
import pl.com.bottega.factory.demand.forecasting.persistence.DocumentEntity
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandEntity
import pl.com.bottega.factory.product.management.ProductDescriptionEntity
import pl.com.bottega.factory.shortages.prediction.calculation.Stock
import pl.com.bottega.factory.shortages.prediction.monitoring.persistence.ShortagesEntity
import pl.com.bottega.factory.warehouse.WarehouseService
import pl.com.bottega.tools.IntegrationTest
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

import static java.time.Instant.from
import static java.time.ZoneId.systemDefault
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@IntegrationTest
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [AppConfiguration, TestConfiguration])
class ShortageIntegrationSpec extends Specification implements ProductTrait {

    public static final String PRODUCT_REF_NO = "3009003"
    public static final LocalDate ANY_DATE = LocalDate.now()
    public static final int PRODUCT_STOCK_LEVEL = 100

    @Autowired TestRestTemplate restTemplate

    def 'adjustment that exceeds current stock level should result in shortage'() {
        given:
            productDescriptionIsSuccessfullyCreated(PRODUCT_REF_NO)
        when:
            callOffDocumentIsSuccessfullyRequested(PRODUCT_REF_NO, ANY_DATE, PRODUCT_STOCK_LEVEL)
        and:
            adjustmentIsSuccessfullyRequested(ANY_DATE.plusDays(1), 200)
        then:
            thereIsShortage(PRODUCT_REF_NO, ANY_DATE.plusDays(1), 200)

    }

    void productDescriptionIsSuccessfullyCreated(String refNo) {
        ResponseEntity response = restTemplate
                .postForEntity("/product-descriptions", productDescription(refNo), ProductDescriptionEntity)
        assert response.statusCode.is2xxSuccessful()
    }

    void callOffDocumentIsSuccessfullyRequested(String refNo, LocalDate date, long ... levels) {
        ResponseEntity response = restTemplate
                .postForEntity("/demand-documents", documentFor(refNo, date, levels), DocumentEntity)
        assert response.statusCode.is2xxSuccessful()
    }

    void adjustmentIsSuccessfullyRequested(LocalDate date, long levelExpected) {
        Map<LocalDate, Adjustment> adjustments = [:]
        adjustments.put(date, new Adjustment(Demand.of(levelExpected), true))
        ResponseEntity response = restTemplate
                .postForEntity("/demand-adjustments", strongAdjustment(PRODUCT_REF_NO, adjustments), DemandAdjustmentEntity)
        assert response.statusCode.is2xxSuccessful()
    }

    void thereIsShortage(String refNo, LocalDate forDate, long expectedShortage) {
        ResponseEntity<Resource<ShortagesEntity>> res = restTemplate
                .exchange("/shortages/search/refNos?refNo={refNo}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resource<ShortagesEntity>>() {},
                ["refNo": refNo])
        assert res.statusCode.is2xxSuccessful()
    }


    @Configuration
    static class TestConfiguration {

        @Bean
        Clock clock() {
            return Clock.fixed(from(ANY_DATE.atStartOfDay().atZone(systemDefault())), systemDefault())
        }

        @Bean
        WarehouseService warehouseService() {
            return { refNo -> new Stock(PRODUCT_STOCK_LEVEL, 0) }
        }
    }
}