package io.dddbyexamples.factory.demand.forecasting

import spock.lang.Specification

import java.time.LocalDate

class DemandAdjustmentSpec extends Specification implements ProductDemandTrait {

    def events = Mock(DemandEvents)

    void setup() {
        builder = new ProductDemandBuilder(events: events)
    }

    def "Adjusted demands should be stored"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800, 0)
        def adjustments = adjustments([(today): 1000])

        when:
        demand.adjust(adjustments)

        then:
        1 * events.emit(levelChanged([2800, 1000]))
    }

    def "Adjustment of future demands is possible"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800)
        def adjustments = adjustments([(today.plusDays(1)): 1000])

        when:
        demand.adjust(adjustments)

        then:
        1 * events.emit(levelChanged(notChanged(), [0, 1000]))
    }

    def "Adjustment without changes should not generate event"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800, 1000)
        def adjustments = adjustments([(today): 2800, (today.plusDays(1)): 1000])

        when:
        demand.adjust(adjustments)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "Should skip past demands adjustments"() {
        given:
        def pastDate = LocalDate.now(builder.clock).minusDays(2)
        def demand = demanded(2800, 0)
        def adjustments = adjustments([(pastDate): 1000])

        when:
        demand.adjust(adjustments)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "Adjustment should be idempotent"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800, 0)
        def adjustments = adjustments((today): 2000, (today.plusDays(1)): 3500)

        when:
        demand.adjust(adjustments)

        then:
        1 * events.emit(levelChanged([2800, 2000], [0, 3500]))

        when:
        demand.adjust(adjustments)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }
}