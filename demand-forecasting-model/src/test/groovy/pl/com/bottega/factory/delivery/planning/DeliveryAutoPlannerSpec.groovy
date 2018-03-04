package pl.com.bottega.factory.delivery.planning

import pl.com.bottega.factory.demand.forecasting.Demand
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Collectors

import static pl.com.bottega.factory.delivery.planning.DeliveriesSuggestion.timesAndFractions
import static pl.com.bottega.factory.demand.forecasting.Demand.Schema.AtDayStart

class DeliveryAutoPlannerSpec extends Specification {

    def final refNo = "3009000"
    def final date = LocalDate.now()
    def final midnight = LocalTime.of(0, 0)
    def final sevenAM = LocalTime.of(7, 0)

    def "Picks dummy suggestion"() {
        given:
        def planner = deliveryDefinitions([:])

        when:
        def deliveries = planner.propose(date, atDayStart(2000))
                .collect(Collectors.toList())

        then:
        deliveries == [deliveryAt(date.atTime(midnight), 2000)]
    }

    def "Suggests according to defined policy"() {
        given:
        def planner = deliveryDefinitions([
                (AtDayStart): timesAndFractions([(sevenAM): 1.0d])
        ])

        when:
        def deliveries = planner.propose(date, atDayStart(2000))
                .collect(Collectors.toList())

        then:
        deliveries == [deliveryAt(date.atTime(sevenAM), 2000)]
    }

    def deliveryDefinitions(Map<Demand.Schema, DeliveriesSuggestion> suggestions) {
        new DeliveryAutoPlanner(refNo, suggestions)
    }

    def deliveryAt(LocalDateTime time, Integer level) {
        new Delivery(refNo, time, level)
    }

    def atDayStart(long level) {
        Demand.of(level, AtDayStart)
    }
}
