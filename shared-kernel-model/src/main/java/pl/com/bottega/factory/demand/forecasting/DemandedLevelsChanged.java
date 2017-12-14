package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.product.management.RefNoId;

import java.util.Collections;
import java.util.Map;

@Value
public class DemandedLevelsChanged {
    RefNoId refNo;
    Map<DailyId, Change> results;

    public DemandedLevelsChanged(RefNoId refNo, Map<DailyId, Change> results) {
        this.refNo = refNo;
        this.results = Collections.unmodifiableMap(results);
    }

    @Value
    public static class Change {
        Demand previous;
        Demand current;
    }
}
