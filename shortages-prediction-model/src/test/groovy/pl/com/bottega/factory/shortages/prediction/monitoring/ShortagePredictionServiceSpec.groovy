package pl.com.bottega.factory.shortages.prediction.monitoring

import pl.com.bottega.factory.demand.forecasting.DemandedLevelsChanged
import spock.lang.Specification

class ShortagePredictionServiceSpec extends Specification implements ShortagePredictionProcessTrait {

    def repo = Mock(ShortagePredictionProcessRepository)
    def service = new ShortagePredictionService(repo)

    def "Repository interactions while processing demanded changed event"() {
        given:
        def process = predictionProcess(
                noShortagesWasPreviouslyFound(),
                noShortagesWillBeFound()
        )
        repo.get(refNo) >> process

        when:
        service.predictShortages(new DemandedLevelsChanged(refNo, [:]))

        then:
        1 * repo.save(process)
        0 * events.emit(_ as ShortageSolved)
    }
}
