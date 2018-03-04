package pl.com.bottega.factory.delivery.planning

import pl.com.bottega.factory.demand.forecasting.Demand
import spock.lang.PendingFeature
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Collectors

import static pl.com.bottega.factory.demand.forecasting.Demand.Schema.AtDayStart

class DeliveriesSuggestionSpec extends Specification {

    def final refNo = "3009000"
    def final date = LocalDate.now()
    def final midnight = LocalTime.of(0, 0)
    def final sevenAM = LocalTime.of(7, 0)
    def final nineAM = LocalTime.of(9, 0)

    def "Times and fractions - all at once"() {
        given:
        def suggestion = DeliveriesSuggestion.timesAndFractions([(sevenAM): 1.0d])

        when:
        def deliveries = suggestion.deliveriesFor(refNo, date, atDayStart(2000))
                .collect(Collectors.toList())

        then:
        deliveries == [deliveryAt(date.atTime(sevenAM), 2000)]
    }

    def "Times and fractions - half twice"() {
        given:
        def suggestion = DeliveriesSuggestion.timesAndFractions([
                (sevenAM): 0.5d,
                (nineAM): 0.5d,
        ])

        when:
        def deliveries = suggestion.deliveriesFor(refNo, date, atDayStart(2000))
                .collect(Collectors.toList())

        then:
        deliveries == [
                deliveryAt(date.atTime(sevenAM), 1000),
                deliveryAt(date.atTime(nineAM), 1000),
        ]
    }

    def "Times and fractions - not represented number"() {
        given:
        def suggestion = DeliveriesSuggestion.timesAndFractions([
                (midnight): 1/3d,
                (sevenAM): 1/3d,
                (nineAM): 1/3d,
        ])

        when:
        def deliveries = suggestion.deliveriesFor(refNo, date, atDayStart(2000))
                .collect(Collectors.toList())

        then:
        deliveries == [
                deliveryAt(date.atTime(midnight), 666),
                deliveryAt(date.atTime(sevenAM), 666),
                deliveryAt(date.atTime(nineAM), 666),
        ]
    }

    @PendingFeature
    def "Times and fractions - do not lost parts on not represented number"() {
        given:
        def suggestion = DeliveriesSuggestion.timesAndFractions([
                (midnight): 1/3d,
                (sevenAM): 1/3d,
                (nineAM): 1/3d,
        ])

        when:
        def deliveries = suggestion.deliveriesFor(refNo, date, atDayStart(2000))
                .collect(Collectors.toList())

        then:
        deliveries == [
                deliveryAt(date.atTime(midnight), 666),
                deliveryAt(date.atTime(sevenAM), 666),
                deliveryAt(date.atTime(nineAM), 668),
        ]
    }

    def deliveryAt(LocalDateTime time, Integer level) {
        new Delivery(refNo, time, level)
    }

    def atDayStart(long level) {
        Demand.of(level, AtDayStart)
    }
}
