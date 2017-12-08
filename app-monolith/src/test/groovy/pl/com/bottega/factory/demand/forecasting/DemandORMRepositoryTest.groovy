package pl.com.bottega.factory.demand.forecasting

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import pl.com.bottega.factory.demand.forecasting.persistence.DemandDao
import pl.com.bottega.factory.demand.forecasting.persistence.ProductDemandDao
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.transaction.Transactional
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@SpringBootTest
@Transactional
@Commit
class DemandORMRepositoryTest extends Specification {

    def clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    def events = Mock(DemandEventsMapping)
    @Autowired
    EntityManager em
    @Autowired
    ProductDemandDao rootDao
    @Autowired
    DemandDao demandDao

    DemandORMRepository repository

    final def today = LocalDate.now(clock)

    def setup() {
        demandDao.deleteAllInBatch()
        rootDao.deleteAllInBatch()
        repository = new DemandORMRepository(clock, events, em, rootDao, demandDao)
    }

    def "persists new demand"() {
        given:
        rootDao.save(new ProductDemandEntity("3009000"))

        when:
        def object = repository.get("3009000")
        object.adjust(new AdjustDemand("3009000", [
                (today): Adjustment.strong(Demand.of(2000))
        ]))
        repository.save(object)

        then:
        demandDao.findAll().size() == 1
    }

    def "updates existing demand"() {
        given:
        def root = rootDao.save(new ProductDemandEntity("3009000"))
        def demand = new DemandEntity(root, today)
        demand.set(Demand.of(1000), null)
        demandDao.save(demand)

        when:
        def object = repository.get("3009000")
        object.adjust(new AdjustDemand("3009000", [
                (today): Adjustment.strong(Demand.of(2000))
        ]))
        repository.save(object)

        then:
        def demands = demandDao.findAll()
        demands.size() == 1
        demand.every { it.get().getAdjustment() == Adjustment.strong(Demand.of(2000)) }
    }

    def "doesn't fetch historical data"() {
        given:
        def root = rootDao.save(new ProductDemandEntity("3009000"))
        def old = new DemandEntity(root, today.minusDays(1))
        old.set(Demand.of(10000), null)
        demandDao.save(old)

        def todays = new DemandEntity(root, today)
        todays.set(Demand.of(1000), null)
        demandDao.save(todays)

        when:
        def demands = demandDao.findByProductRefNoAndDateGreaterThanEqual("3009000", today)

        then:
        demands.size() == 1
        demands.contains(todays)
        !demands.contains(old)
    }
}
