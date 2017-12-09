package pl.com.bottega.factory.shortages.prediction.monitoring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import pl.com.bottega.factory.product.management.RefNoId
import pl.com.bottega.factory.shortages.prediction.Shortages
import pl.com.bottega.factory.shortages.prediction.calculation.Forecasts
import pl.com.bottega.factory.shortages.prediction.notification.NotificationOfShortage
import spock.lang.Specification

import javax.transaction.Transactional
import java.time.LocalDateTime

import static pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage.After.DemandChanged

@SpringBootTest
@Transactional
@Commit
class ShortagePredictionProcessORMRepositoryTest extends Specification {

    def now = LocalDateTime.now()
    def refNo = "3009000"

    @Autowired
    ShortagesDao dao
    def forecasts = Mock(Forecasts)
    def notifications = Mock(NotificationOfShortage)
    ShortagePredictionProcessORMRepository repository

    def setup() {
        dao.deleteAll()
        repository = new ShortagePredictionProcessORMRepository(
                dao, forecasts, notifications
        )
    }

    def "provides process instance when no shortage persisted"() {
        when:
        def process = fetchProcess()

        then:
        shortagesCurrentlyKnownBy(process) == noShortages()
    }

    def "provides process instance with last known shortage"() {
        given:
        persistedShortage(someShortages())

        when:
        def process = fetchProcess()

        then:
        shortagesCurrentlyKnownBy(process) == someShortages()
    }

    def "persists first shortage"() {
        when:
        def process = fetchProcess()
        processEmitsNewShortage(process, someShortages())

        then:
        shortagesCurrentlyPersisted() == someShortages()
    }

    def "updates previous shortage"() {
        given:
        persistedShortage(someOldShortages())

        when:
        def process = fetchProcess()
        processEmitsNewShortage(process, someShortages())

        then:
        shortagesCurrentlyPersisted() == someShortages()
    }

    def "deletes solved shortage"() {
        given:
        persistedShortage(someShortages())

        when:
        def process = fetchProcess()
        processEmitsShortageSolved(process)

        then:
        noShortagesPersisted()
    }

    def persistedShortage(Shortages shortages) {
        def entity = new ShortagesEntity(refNo)
        entity.setShortages(shortages)
        dao.save(entity)
    }

    Shortages shortagesCurrentlyPersisted() {
        dao.findByRefNo(refNo).get().shortages
    }

    void noShortagesPersisted() {
        assert dao.findByRefNo(refNo) == Optional.empty()
    }

    ShortagePredictionProcess fetchProcess() {
        repository.get(new RefNoId(refNo))
    }

    Shortages noShortages() {
        null
    }

    Shortages someShortages() {
        Shortages.builder(refNo, 0, now)
                .missing(now.plusDays(1), 500)
                .build()
                .orElse(null)
    }

    Shortages someOldShortages() {
        Shortages.builder(refNo, 0, now.minusDays(1))
                .missing(now.plusDays(2), 2500)
                .build()
                .orElse(null)
    }

    Shortages shortagesCurrentlyKnownBy(ShortagePredictionProcess process) {
        process.known
    }

    void processEmitsNewShortage(ShortagePredictionProcess process, Shortages shortages) {
        process.events.emit(new NewShortage(process.refNo, DemandChanged, shortages))
    }

    void processEmitsShortageSolved(ShortagePredictionProcess process) {
        process.events.emit(new ShortageSolved(process.refNo))
    }
}
