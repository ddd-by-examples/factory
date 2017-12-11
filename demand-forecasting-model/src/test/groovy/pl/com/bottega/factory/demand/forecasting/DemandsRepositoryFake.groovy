package pl.com.bottega.factory.demand.forecasting

import java.time.Clock
import java.time.LocalDate

class DemandsRepositoryFake extends Demands {

    DailyDemandBuilder builder

    DemandsRepositoryFake(String refNo, UnitOfWork unitOfWork, Clock clock) {
        this.builder = new DailyDemandBuilder(refNo: refNo, events: unitOfWork, clock: clock)
        fetch = { date -> nothingDemanded(date) }
    }

    DailyDemand nothingDemanded(LocalDate date) {
        def demand = builder.reset()
                .date(date)
                .build()
        fetched.put(date, demand)
        demand
    }

    DailyDemand demanded(LocalDate date, long level) {
        def demand = builder.date(date)
                .demandedLevels(level)
                .noAdjustments()
                .build()
        fetched.put(date, demand)
        demand
    }
}
