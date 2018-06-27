package io.dddbyexamples.factory.integration

import io.dddbyexamples.factory.AppConfiguration
import io.dddbyexamples.factory.ProductTrait
import io.dddbyexamples.factory.demand.forecasting.persistence.DocumentEntity
import io.dddbyexamples.factory.demand.forecasting.projection.CurrentDemandEntity
import io.dddbyexamples.factory.product.management.ProductDescriptionEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDate

import static java.time.Instant.from
import static java.time.ZoneId.systemDefault
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [AppConfiguration, TestConfiguration])
class CallOffDocumentIntegrationSpec extends Specification implements ProductTrait {

    public static final String PRODUCT_REF_NO = "3009000"
    public static final LocalDate ANY_DATE = LocalDate.now()

    @Autowired
    TestRestTemplate restTemplate

    def 'receiving call off document should create new product demands for subsequent days'() {
        given:
        productDescriptionIsSuccessfullyCreated(PRODUCT_REF_NO)
        when:
        callOffDocumentIsSuccessfullyRequested(PRODUCT_REF_NO, ANY_DATE, 100, 200, 300)
        and:
        Collection<CurrentDemandEntity> demands =
                demandsForProductStartingFromDateAreRequested(PRODUCT_REF_NO, ANY_DATE.minusDays(1))
        then:
        demands.size() == 3
        thereIsDemand(demands, ANY_DATE, 100)
        thereIsDemand(demands, ANY_DATE.plusDays(1), 200)
        thereIsDemand(demands, ANY_DATE.plusDays(2), 300)
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

    void thereIsDemand(Collection<CurrentDemandEntity> demands, LocalDate date, long expectedLevel) {
        assert demands.find { it.date == date && it.level == expectedLevel }
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        Clock clock() {
            return Clock.fixed(from(ANY_DATE.atStartOfDay().atZone(systemDefault())), systemDefault())
        }
    }
}
