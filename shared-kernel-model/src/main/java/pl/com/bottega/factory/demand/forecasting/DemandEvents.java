package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.product.management.RefNoId;

import java.util.Collections;
import java.util.Map;

public interface DemandEvents {
    void emit(DemandedLevelsChanged event);

    @Value
    class DemandedLevelsChanged {
        RefNoId id;
        Map<DailyId, Change> results;

        public DemandedLevelsChanged(RefNoId id, Map<DailyId, Change> results) {
            this.id = id;
            this.results = Collections.unmodifiableMap(results);
        }

        @Value
        public static class Change {
            Demand previous;
            Demand current;
        }
    }
}
