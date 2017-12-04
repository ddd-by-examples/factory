package pl.com.bottega.factory.demand.forecasting

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

import static pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged.Change;

class DailyDemandBuilder {

    Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    DailyDemand.Events events

    String refNo = "3009000"
    private LocalDate date = LocalDate.now(clock)
    private Demand base
    private Adjustment adjustment

    DailyDemand build() {
        new DailyDemand(new DailyId(refNo, date), events, base, adjustment)
    }

    DailyDemandBuilder reset() {
        nothingDemanded()
        noAdjustments()
    }

    Object asType(Class clazz) {
        clazz == DailyDemand ? build() : super.asType(clazz)
    }

    DailyDemandBuilder events(DailyDemand.Events events) {
        this.events = events
        this
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

    DailyDemand.LevelChanged levelChanged(long previous, long current) {
        new DailyDemand.LevelChanged(
                new DailyId(refNo, date),
                new Change(Demand.of(previous), Demand.of(current))
        )
    }
}
