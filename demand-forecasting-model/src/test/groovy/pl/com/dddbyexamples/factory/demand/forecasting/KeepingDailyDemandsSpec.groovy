package pl.com.dddbyexamples.factory.demand.forecasting

import pl.com.dddbyexamples.factory.demand.forecasting.Adjustment
import pl.com.dddbyexamples.factory.demand.forecasting.Demand
import spock.lang.Specification

class KeepingDailyDemandsSpec extends Specification {

    def builder = new DailyDemandBuilder()

    def "Adjusted demands should be stored"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .noAdjustments().build()

        when:
        def res = demand.adjust(adjustDemandTo(3500))

        then:
        demand.getLevel() == Demand.of(3500)
        res.levelChange == levelChanged(2800, 3500)
        res.updated != null
    }

    def "Adjusted demands should be stored when there is no demand for product"() {
        given:
        def demand = demand()
                .nothingDemanded()
                .noAdjustments().build()

        when:
        def res = demand.adjust(adjustDemandTo(3500))

        then:
        demand.getLevel() == Demand.of(3500)
        res.levelChange == levelChanged(0, 3500)
        res.updated != null
    }

    def "In standard case documented demands overrides adjustments"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .adjustedTo(3500).build()

        when:
        def res = demand.update(newCallOffDemand(4000))

        then:
        demand.getLevel() == Demand.of(4000)
        res.levelChange == levelChanged(3500, 4000)
        res.updated != null
    }

    def "Strong adjustment is kept even after processing of document"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .stronglyAdjustedTo(3500).build()

        when:
        def res = demand.update(newCallOffDemand(2800))

        then:
        demand.getLevel() == Demand.of(3500)
        res.levelChange == null
        res.updated == null
    }

    def "Document update ignored by strong adjustment should rise warning"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .stronglyAdjustedTo(3500).build()

        when:
        def res = demand.update(newCallOffDemand(5000))

        then:
        demand.getLevel() == Demand.of(3500)
        res.toReview == reviewRequest(2800, 3500, 5000)
        res.levelChange == null
        res.updated != null
    }

    DailyDemandBuilder demand() {
        builder
    }

    Demand newCallOffDemand(long level) {
        builder.newCallOffDemand(level)
    }

    Adjustment adjustDemandTo(long level) {
        builder.adjustDemandTo(level)
    }

    def levelChanged(long previous, long current) {
        builder.levelChanged(previous, current)
    }

    def reviewRequest(long previousDocumented, long adjustment, long newDocumented) {
        builder.reviewRequest(previousDocumented, adjustment, newDocumented)
    }
}
