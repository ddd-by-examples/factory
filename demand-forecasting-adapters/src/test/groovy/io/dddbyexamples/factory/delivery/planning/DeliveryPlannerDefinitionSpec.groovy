package io.dddbyexamples.factory.delivery.planning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import io.dddbyexamples.factory.delivery.planning.definition.DeliveryPlannerDefinition
import io.dddbyexamples.factory.delivery.planning.definition.DeliveryPlannerDefinitionDao
import io.dddbyexamples.factory.delivery.planning.definition.DeliveryPlannerDefinitionEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import static java.time.LocalTime.of as time
import static io.dddbyexamples.factory.delivery.planning.definition.DeliveryPlannerDefinition.of
import static io.dddbyexamples.factory.demand.forecasting.Demand.Schema.*

@ActiveProfiles("test")
@SpringBootTest
class DeliveryPlannerDefinitionSpec extends Specification {

    @Autowired
    DeliveryPlannerDefinitionDao dao

    void setup() {
        dao.deleteAllInBatch()
    }

    def "verify access to DeliveryPlannerDefinition data"() {
        given:
        def definition = DeliveryPlannerDefinition.builder()
                .definition(AtDayStart, of(time(06, 00), 1.0d))
                .definition(TillDayEnd, of(time(22, 00), 1.0d))
                .definition(Twice, [(time(16, 00)): 0.5d, (time(20, 00)): 0.5d])
                .build()

        dao.save(new DeliveryPlannerDefinitionEntity("3009000", definition))

        when:
        def entities = dao.findAll()

        then:
        entities.size() == 1
        entities.get(0).definition == definition
    }
}
