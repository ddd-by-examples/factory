package io.dddbyexamples.factory.demand.forecasting

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class DailyDemandBuilder {

    Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    ReviewPolicy policy = ReviewPolicy.BASIC

    String refNo = "3009000"
    private LocalDate date = LocalDate.now(clock)
    private Demand base
    private Adjustment adjustment

    DailyDemand build() {
        new DailyDemand(new DailyId(refNo, date), policy, base, adjustment)
    }

    DailyDemandBuilder reset() {
        nothingDemanded()
        noAdjustments()
    }

    Object asType(Class clazz) {
        clazz == DailyDemand ? build() : super.asType(clazz)
    }

    DailyDemandBuilder nextDate() {
        date.plusDays(1)
        this
    }

    DailyDemandBuilder date(LocalDate date) {
        this.date = date
        this
    }

    DailyDemandBuilder nothingDemanded() {
        base = null
        this
    }

    DailyDemandBuilder noAdjustments() {
        adjustment = null
        this
    }

    DailyDemandBuilder demandedLevels(long level) {
        base = Demand.of(level)
        this
    }

    DailyDemandBuilder demandedLevels(Demand level) {
        base = level
        this
    }

    DailyDemandBuilder adjustedTo(long level) {
        adjustment = new Adjustment(Demand.of(level), false)
        this
    }

    DailyDemandBuilder stronglyAdjustedTo(long level) {
        adjustment = new Adjustment(Demand.of(level), true)
        this
    }

    Demand newCallOffDemand(long level) {
        Demand.of(level)
    }

    Adjustment adjustDemandTo(long level) {
        new Adjustment(Demand.of(level), false)
    }

    DemandedLevelsChanged.Change levelChanged(long previous, long current) {
        new DemandedLevelsChanged.Change(Demand.of(previous), Demand.of(current))
    }

    ReviewRequired.ToReview reviewRequest(long previousDocumented, long adjustment, long newDocumented) {
        new ReviewRequired.ToReview(
                new DailyId(refNo, date),
                Demand.of(previousDocumented),
                Demand.of(adjustment),
                Demand.of(newDocumented)
        )
    }
}
