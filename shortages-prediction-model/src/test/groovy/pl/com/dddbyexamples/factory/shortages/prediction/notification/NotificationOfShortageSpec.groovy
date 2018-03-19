package pl.com.dddbyexamples.factory.shortages.prediction.notification

import pl.com.dddbyexamples.factory.product.management.RefNoId
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.NewShortage
import pl.com.dddbyexamples.factory.shortages.prediction.notification.NotificationOfShortage
import pl.com.dddbyexamples.factory.shortages.prediction.notification.QualityTasks
import pl.com.dddbyexamples.factory.shortages.prediction.notification.RecoveryTaskPriorityChangePolicy
import spock.lang.Specification

import java.time.*

import static NewShortage.After

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
        notificator.notifyAbout(newShortage(After.DemandChanged, withShortage()))

        then:
        1 * notifications.alertPlanner(withShortage())
    }

    def "Warns planner after DemandChanged"() {
        given:
        def notificator = notificator()

        when:
        notificator.notifyAbout(newShortage(After.LockedParts, withShortage()))

        then:
        1 * notifications.softNotifyPlanner(withShortage())
    }

    def "Marks shortage on plan during adjustment of plan"() {
        given:
        def notificator = notificator()

        when:
        notificator.notifyAbout(newShortage(After.PlanChanged, withShortage()))

        then:
        1 * notifications.markOnPlan(withShortage())
    }

    def "Alerts planner after StockChanged"() {
        given:
        def notificator = notificator()

        when:
        notificator.notifyAbout(newShortage(After.StockChanged, withShortage()))

        then:
        1 * notifications.alertPlanner(withShortage())
    }

    def "Increase task priority if there are locked parts and shortage is 'close'"() {
        given:
        def notificator = notificator()

        when:
        notificator.notifyAbout(newShortage(After.StockChanged,
                withShortage(Duration.ofDays(1), 500))
        )

        then:
        1 * tasks.increasePriorityFor(refNo)
    }

    def "Don't try to increase task priority if there are NO locked parts"() {
        given:
        def notificator = notificator()

        when:
        notificator.notifyAbout(newShortage(After.StockChanged,
                withShortage(Duration.ofDays(1), 0))
        )

        then:
        0 * tasks.increasePriorityFor(_)
    }

    def "Don't try to increase task priority if there is shortage in far future"() {
        given:
        def notificator = notificator()

        when:
        notificator.notifyAbout(newShortage(After.StockChanged,
                withShortage(Duration.ofDays(10), 500))
        )

        then:
        0 * tasks.increasePriorityFor(_)
    }

    def notificator() {
        new NotificationOfShortage(
                tasks, clock, policy,
                NotificationOfShortage.rulesOfPlannerNotification(notifications)
        )
    }

    NewShortage newShortage(After after, Shortage shortages) {
        new NewShortage(new RefNoId(refNo), after, shortages)
    }

    Shortage withShortage(
            Duration firstShortageIn = Duration.ofDays(4),
            long lockedStock = 0) {
        Shortage.builder(refNo, lockedStock, now)
                .missing(now.plus(firstShortageIn), 500L)
                .build().get()
    }
}
