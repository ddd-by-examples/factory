package pl.com.bottega.factory.shortages.prediction.notification

import pl.com.bottega.factory.shortages.prediction.Shortage
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

class RecoveryTaskPriorityChangePolicySpec extends Specification {

    def now = LocalDateTime.now()

    Shortage foundShortage(Duration firstShortageIn, long lockedStock) {
        Shortage.builder("3009000", lockedStock, now)
                .missing(now.plus(firstShortageIn), 500L)
                .build().get()
    }

    def "'never policy' don't increase priority... ever"() {
        given:
        def policy = RecoveryTaskPriorityChangePolicy.never()

        expect:
        policy.shouldIncreasePriority(
                now,
                foundShortage(firstShortageIn, lockedStock)
        ) == policyDecision

        where:
        firstShortageIn       | lockedStock || policyDecision
        Duration.ofMinutes(5) | 500L        || false
        Duration.ofMinutes(5) | 0L          || false
        Duration.ofDays(15)   | 500L        || false
        Duration.ofDays(15)   | 0L          || false
    }

    def "'onlyIn1DaysAhead policy' increase priority for shortages in 24h"() {
        given:
        def policy = RecoveryTaskPriorityChangePolicy.onlyIn1DaysAhead()

        expect:
        policy.shouldIncreasePriority(
                now,
                foundShortage(firstShortageIn, lockedStock)
        ) == policyDecision

        where:
        firstShortageIn                     | lockedStock || policyDecision
        Duration.ofMinutes(5)               | 500L        || true
        Duration.ofMinutes(5)               | 0L          || false

        Duration.ofHours(24).minusMillis(1) | 500L        || true
        Duration.ofHours(24)                | 500L        || false
        Duration.ofHours(24).plusMillis(1)  | 500L        || false
    }

    def "'shortageInDays(2) policy' increase priority for shortages in 48h"() {
        given:
        def policy = RecoveryTaskPriorityChangePolicy.shortageInDays(2)

        expect:
        policy.shouldIncreasePriority(
                now,
                foundShortage(firstShortageIn, lockedStock)
        ) == policyDecision

        where:
        firstShortageIn                     | lockedStock || policyDecision
        Duration.ofMinutes(5)               | 500L        || true
        Duration.ofMinutes(5)               | 0L          || false

        Duration.ofHours(48).minusMillis(1) | 500L        || true
        Duration.ofHours(48)                | 500L        || false
        Duration.ofHours(48).plusMillis(1)  | 500L        || false
    }
}
