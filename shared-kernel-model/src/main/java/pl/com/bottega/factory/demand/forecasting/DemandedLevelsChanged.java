package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.product.management.RefNoId;

import java.util.Map;

@Value
public class DemandedLevelsChanged {
    RefNoId refNo;
    Map<DailyId, Change> results;

    @Value
    public static class Change {
        Demand previous;
        Demand current;
    }
}
