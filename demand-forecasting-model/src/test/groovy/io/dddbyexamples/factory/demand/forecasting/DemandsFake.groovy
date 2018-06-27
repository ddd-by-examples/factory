package io.dddbyexamples.factory.demand.forecasting

import java.time.Clock
import java.time.LocalDate

class DemandsFake extends Demands {

    DailyDemandBuilder builder

    DemandsFake(String refNo, Clock clock) {
        this.builder = new DailyDemandBuilder(refNo: refNo, clock: clock)
        fetch = { date -> nothingDemanded(date) }
    }

    DailyDemand demanded(LocalDate date, long level) {
        def demand = builder.date(date)
                .demandedLevels(level)
                .noAdjustments()
                .build()
        fetched.put(date, demand)
        demand
    }

    DailyDemand adjusted(LocalDate date, long level) {
        def demand = builder.date(date)
                .demandedLevels(fetched.get(date)?.level)
                .adjustedTo(level)
                .build()

        fetched.put(date, demand)
        demand
    }

    DailyDemand stronglyAdjusted(LocalDate date, long level) {
        def demand = builder.date(date)
                .demandedLevels(fetched.get(date)?.level)
                .stronglyAdjustedTo(level)
                .build()

        fetched.put(date, demand)
        demand
    }

    private DailyDemand nothingDemanded(LocalDate date) {
        def demand = builder.reset()
                .date(date)
                .build()
        demand
    }
}
