package pl.com.dddbyexamples.factory.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import pl.com.dddbyexamples.factory.AppConfiguration
import pl.com.dddbyexamples.factory.ProductTrait
import pl.com.dddbyexamples.factory.demand.forecasting.Adjustment
import pl.com.dddbyexamples.factory.demand.forecasting.Demand
import pl.com.dddbyexamples.factory.demand.forecasting.command.DemandAdjustmentEntity
import pl.com.dddbyexamples.factory.demand.forecasting.persistence.DocumentEntity
import pl.com.dddbyexamples.factory.demand.forecasting.projection.CurrentDemandEntity
import pl.com.dddbyexamples.factory.product.management.ProductDescriptionEntity
import pl.com.dddbyexamples.tools.IntegrationTest
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

import static java.time.Instant.from
import static java.time.ZoneId.systemDefault
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@IntegrationTest
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [AppConfiguration, TestConfiguration])
class DemandAdjustmentIntegrationSpec extends Specification implements ProductTrait {

    public static final String PRODUCT_REF_NO = "3009001"
    public static final LocalDate ANY_DATE = LocalDate.now()

    @Autowired TestRestTemplate restTemplate

    def 'receiving future adjustment should result in demand being changed for specific date'() {
        given:
            productDescriptionIsSuccessfullyCreated(PRODUCT_REF_NO)
        when:
            callOffDocumentIsSuccessfullyRequested(PRODUCT_REF_NO, ANY_DATE, 100, 200, 300)
        then:
            thereIsDemand(ANY_DATE.plusDays(1), 200)
        when:
            adjustmentIsSuccessfullyRequested(ANY_DATE.plusDays(1), 400)
        then:
            thereIsDemand(ANY_DATE.plusDays(1), 400)

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

    void thereIsDemand(LocalDate date, long expectedLevel) {
        Collection<CurrentDemandEntity> demands =
                demandsForProductStartingFromDateAreRequested(PRODUCT_REF_NO, ANY_DATE.minusDays(1))
        assert demands.find { it.date == date && it.level == expectedLevel }
    }

    Collection<CurrentDemandEntity> demandsForProductStartingFromDateAreRequested(String refNo, LocalDate date) {
        ResponseEntity<Resources<CurrentDemandEntity>> res = restTemplate
                .exchange("/demand-forecasts/search/refNos?refNo={refNo}&date={date}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resources<CurrentDemandEntity>>() {},
                ["refNo": refNo, "date": date])
        assert res.statusCode.is2xxSuccessful()
        return res.getBody().getContent()
    }

    void adjustmentIsSuccessfullyRequested(LocalDate date, long levelExpected) {
        Map<LocalDate, Adjustment> adjustments = [:]
        adjustments.put(date, new Adjustment(Demand.of(levelExpected), true))
        ResponseEntity response = restTemplate
                .postForEntity("/demand-adjustments", strongAdjustment(PRODUCT_REF_NO, adjustments), DemandAdjustmentEntity)
        assert response.statusCode.is2xxSuccessful()
    }


    @Configuration
    static class TestConfiguration {

        @Bean
        Clock clock() {
            return Clock.fixed(from(ANY_DATE.atStartOfDay().atZone(systemDefault())), systemDefault())
        }
    }
}