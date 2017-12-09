package pl.com.bottega.factory.shortages.prediction.notification

import pl.com.bottega.factory.product.management.RefNoId
import pl.com.bottega.factory.shortages.prediction.Shortages
import pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage
import pl.com.bottega.factory.shortages.prediction.monitoring.ShortageSolved
import spock.lang.Specification

import java.time.*

import static pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage.After

class NotificationOfShortageSpec extends Specification {

    def refNo = "3009000"
    def now = LocalDateTime.now()

    def notifications = Mock(Notifications)
    def tasks = Mock(QualityTasks)
    def clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    def policy = RecoveryTaskPriorityChangePolicy.shortageInDays(2)

    def "Alerts planner after DemandChanged"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.DemandChanged, withShortage()))

        then:
        1 * notifications.alertPlanner(withShortage())
    }

    def "Warns planner after DemandChanged"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.LockedParts, withShortage()))

        then:
        1 * notifications.softNotifyPlanner(withShortage())
    }

    def "Marks shortage on plan during adjustment of plan"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.PlanChanged, withShortage()))

        then:
        1 * notifications.markOnPlan(withShortage())
    }

    def "Alerts planner after StockChanged"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.StockChanged, withShortage()))

        then:
        1 * notifications.alertPlanner(withShortage())
    }

    def "Increase task priority if there are locked parts and shortage is 'close'"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.StockChanged,
                withShortage(Duration.ofDays(1), 500))
        )

        then:
        1 * tasks.increasePriorityFor(refNo)
    }

    def "Don't try to increase task priority if there are NO locked parts"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.StockChanged,
                withShortage(Duration.ofDays(1), 0))
        )

        then:
        0 * tasks.increasePriorityFor(_)
    }

    def "Don't try to increase task priority if there is shortage in far future"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(newShortage(After.StockChanged,
                withShortage(Duration.ofDays(10), 500))
        )

        then:
        0 * tasks.increasePriorityFor(_)
    }

    def "No notification after shortage solved specified for now"() {
        given:
        def notificator = notificator()

        when:
        notificator.emit(new ShortageSolved(new RefNoId(refNo)))

        then:
        0 * tasks.increasePriorityFor(_)
        0 * notifications._(_)
    }

    def notificator() {
        new NotificationOfShortage(
                tasks, clock, policy,
                NotificationOfShortage.rulesOfPlannerNotification(notifications)
        )
    }

    NewShortage newShortage(After after, Shortages shortages) {
        new NewShortage(new RefNoId(refNo), after, shortages)
    }

    Shortages withShortage(
            Duration firstShortageIn = Duration.ofDays(4),
            long lockedStock = 0) {
        Shortages.builder(refNo, lockedStock, now)
                .missing(now.plus(firstShortageIn), 500L)
                .build().get()
    }
}
