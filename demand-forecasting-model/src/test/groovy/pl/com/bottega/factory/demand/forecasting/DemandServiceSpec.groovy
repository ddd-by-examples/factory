package pl.com.bottega.factory.demand.forecasting

import spock.lang.Specification

import java.time.LocalDate

import static pl.com.bottega.factory.demand.forecasting.ReviewDecision.PICK_NEW

class DemandServiceSpec extends Specification implements ProductDemandTrait {

    def events = Mock(DemandEvents)
    def repo = Mock(ProductDemandRepository)
    def service = new DemandService(repo)

    void setup() {
        builder = new ProductDemandBuilder(events: events)
    }

    def "Repository interactions while product demand initialization"() {
        given:
        def refNo = "3009000"

        when:
        service.initNewProduct(refNo)

        then:
        1 * repo.initNewProduct(refNo)
    }

    def "Repository interactions while document processing"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800, 0)
        def document = document(today, 2000, 3500)
        repo.get(document.refNo) >> demand

        when:
        service.process(document)

        then:
        1 * repo.save(demand)
    }

    def "Repository interactions while demand adjustments"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800, 0)
        def adjustments = adjustments([(today): 1000])
        repo.get(adjustments.refNo) >> demand

        when:
        service.adjust(adjustments)

        then:
        1 * repo.save(demand)
    }

    def "Repository interactions while review processing"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demand(2800)
                .stronglyAdjusted((today): 3500)
                .build()
        def review = reviewDecision(review(today, 0, 3500, 2800), PICK_NEW)
        repo.get(review.refNo) >> demand

        when:
        service.review(review)

        then:
        1 * repo.save(demand)
    }
}
