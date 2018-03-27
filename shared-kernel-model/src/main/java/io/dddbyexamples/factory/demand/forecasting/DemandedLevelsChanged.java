package io.dddbyexamples.factory.demand.forecasting;

import lombok.Value;
import io.dddbyexamples.factory.product.management.RefNoId;

import java.util.Map;

@Value
public class DemandedLevelsChanged {
    RefNoId refNo;
    Map<DailyId, Change> results;

    @Value
    public static class Change {
        Demand previous;
        Demand current;

        public long getDiff() {
            return previous.getLevel() - current.getLevel();
        }
    }
}
