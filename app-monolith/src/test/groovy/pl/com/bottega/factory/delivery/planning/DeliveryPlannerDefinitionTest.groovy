package pl.com.bottega.factory.delivery.planning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static java.time.LocalTime.of as time
import static pl.com.bottega.factory.delivery.planning.DeliveryPlannerDefinition.of
import static pl.com.bottega.factory.demand.forecasting.Demand.Schema.*

@SpringBootTest
class DeliveryPlannerDefinitionTest extends Specification {

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
