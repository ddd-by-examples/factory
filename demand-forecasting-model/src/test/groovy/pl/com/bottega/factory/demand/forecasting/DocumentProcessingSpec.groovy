package pl.com.bottega.factory.demand.forecasting

import spock.lang.Specification

import java.time.LocalDate

import static pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged

class DocumentProcessingSpec extends Specification {

    def events = Mock(DemandEvents)
    def builder = new ProductDemandBuilder(events: events)

    def "Updated demands should be stored"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demand(2800, 0)
        def document = document(today, 2000, 3500)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged([2800, 2000], [0, 3500]))
    }

    def "Demands for dates not present in system should be stored "() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demand(1000)
        def document = document(today, 1000, 3500, 1000)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged(notChanged(), [0, 3500], [0, 1000]))
    }

    def "Document without changes should not generate event"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demand(2800, 0)
        def document = document(today, 2800, 0)

        when:
        demand.process(document)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "Should skip past demands from document"() {
        given:
        def pastDate = LocalDate.now(builder.clock).minusDays(2)
        def demand = demand(0, 0)
        def document = document(pastDate, 2800, 2800, 3500, 1000)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged([0, 3500], [0, 1000]))
    }

    def "Document processing should be idempotent"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demand(2800, 0)
        def document = document(today, 2000, 3500)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged([2800, 2000], [0, 3500]))

        when:
        builder.clearUnitOfWork()
        demand.process(document)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    ProductDemand demand(long ... levels) {
        builder.demand(levels)
    }

    Document document(LocalDate date, long ... levels) {
        builder.document(date, levels)
    }

    DemandedLevelsChanged levelChanged(List<Long>... changes) {
        builder.levelChanged(changes)
    }

    List<Long> notChanged() {
        []
    }
}