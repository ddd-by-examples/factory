package pl.com.bottega.factory.demand.forecasting;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

public class DemandsFake extends Demands {

    public DemandsFake(Map<LocalDate, DailyDemand> fetched,
                       Function<LocalDate, DailyDemand> factory) {
        super();
        this.fetched.putAll(fetched);
        this.fetch = factory;
    }

    public void clearUnitOfWork() {
        this.changes.clear();
        this.warnings.clear();
    }
}
