package pl.com.dddbyexamples.factory.demand.forecasting

import pl.com.dddbyexamples.factory.demand.forecasting.DemandEvents
import pl.com.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged
import spock.lang.Specification

import java.time.LocalDate

import static pl.com.dddbyexamples.factory.demand.forecasting.ReviewDecision.*

class ReviewProcessingSpec extends Specification implements ProductDemandTrait {

    def events = Mock(DemandEvents)

    void setup() {
        builder = new ProductDemandBuilder(events: events)
    }

    def "Review requested"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0, 0)
                .stronglyAdjusted((tomorrow): 3500)
                .build()

        when:
        demand.process(document(today, 0, 2800))

        then:
        1 * events.emit(reviewRequest(review(tomorrow, 0, 3500, 2800)))
    }

    def "decision to 'ignore'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0, 2800)
                .stronglyAdjusted((tomorrow): 3500)
                .build()

        when:
        demand.review(reviewDecision(
                review(tomorrow, 0, 3500, 2800),
                IGNORE
        ))

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "decision to 'pick new'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0, 2800)
                .stronglyAdjusted((tomorrow): 3500)
                .build()

        when:
        demand.review(reviewDecision(
                review(tomorrow, 0, 3500, 2800),
                PICK_NEW
        ))

        then:
        1 * events.emit(levelChanged([], [3500, 2800]))
    }

    def "decision to 'pick previous'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0, 2800)
                .stronglyAdjusted((tomorrow): 3500)
                .build()

        when:
        demand.review(reviewDecision(
                review(tomorrow, 0, 3500, 2800),
                PICK_PREVIOUS
        ))

        then:
        1 * events.emit(levelChanged([], [3500, 0]))
    }

    def "decision to 'make adjustment weak'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0, 2800)
                .stronglyAdjusted((tomorrow): 3500)
                .build()

        when:
        demand.review(reviewDecision(
                review(tomorrow, 0, 3500, 2800),
                MAKE_ADJUSTMENT_WEAK
        ))

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }
}
