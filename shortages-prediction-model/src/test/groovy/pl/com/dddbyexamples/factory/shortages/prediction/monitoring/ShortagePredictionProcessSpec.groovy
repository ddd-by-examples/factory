package pl.com.dddbyexamples.factory.shortages.prediction.monitoring

import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.NewShortage
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.ShortageEvents
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.ShortageSolved
import spock.lang.Specification

import static pl.com.dddbyexamples.factory.shortages.prediction.monitoring.NewShortage.After

class ShortagePredictionProcessSpec extends Specification implements ShortagePredictionProcessTrait {

    void setup() {
        events = Mock(ShortageEvents)
    }

    def "Emits no events when there is still no shortages"() {
        given:
        def process = predictionProcess(
                noShortagesWasPreviouslyFound(),
                noShortagesWillBeFound()
        )

        when:
        process.onDemandChanged()

        then:
        0 * events.emit(_ as NewShortage)
        0 * events.emit(_ as ShortageSolved)
    }

    def "Emits NewShortage found when shortage was found first time"() {
        given:
        def process = predictionProcess(
                noShortagesWasPreviouslyFound(),
                willFindShortages(someShortages())
        )

        when:
        process.onDemandChanged()

        then:
        1 * events.emit(newShortage(After.DemandChanged, someShortages()))
        0 * events.emit(_ as ShortageSolved)
    }

    def "Emits ShortageSolved when shortage disappear"() {
        given:
        def process = predictionProcess(
                wasPreviouslyFound(someShortages()),
                noShortagesWillBeFound()
        )

        when:
        process.onDemandChanged()

        then:
        0 * events.emit(_ as NewShortage)
        1 * events.emit(shortageSolved())
    }

    def "Emits no events when there is still 'same' shortages"() {
        given:
        def process = predictionProcess(
                wasPreviouslyFound(someShortages()),
                willFindShortages(someShortages())
        )

        when:
        process.onDemandChanged()

        then:
        0 * events.emit(_ as NewShortage)
        0 * events.emit(_ as ShortageSolved)
    }

    def "Emits NewShortage found when 'different' shortages will be found"() {
        given:
        def process = predictionProcess(
                wasPreviouslyFound(someShortages()),
                willFindShortages(someDifferentShortages())
        )

        when:
        process.onDemandChanged()

        then:
        1 * events.emit(newShortage(After.DemandChanged, someDifferentShortages()))
        0 * events.emit(_ as ShortageSolved)
    }

    def "Remembers last found shortage"() {
        given:
        def process = predictionProcess(
                noShortagesWasPreviouslyFound(),
                willFindShortages(someShortages())
        )

        when:
        process.onDemandChanged()

        then:
        1 * events.emit(newShortage(After.DemandChanged, someShortages()))
        0 * events.emit(_ as ShortageSolved)

        when:
        process.onDemandChanged()
        process.onLockedParts()
        process.onPlanChanged()
        process.onStockChanged()

        then:
        0 * events.emit(_ as NewShortage)
        0 * events.emit(_ as ShortageSolved)
    }
}
