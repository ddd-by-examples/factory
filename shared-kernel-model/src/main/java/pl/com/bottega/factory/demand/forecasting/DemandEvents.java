package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface DemandEvents {
    void emit(DemandedLevelsChanged event);

    void emit(ReviewRequested event);

    @Value
    class DemandedLevelsChanged {
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

    @Value
    class ReviewRequested {
        RefNoId refNo;
        List<ReviewNeeded> reviews;

        public ReviewRequested(RefNoId refNo, List<ReviewNeeded> reviews) {
            this.refNo = refNo;
            this.reviews = Collections.unmodifiableList(reviews);
        }

        @Value
        public static class ReviewNeeded {
            DailyId id;
            Demand previousDocumented;
            Demand adjustment;
            Demand newDocumented;

            public String getRefNo() {
                return id.getRefNo();
            }

            public LocalDate getDate() {
                return id.getDate();
            }
        }
    }
}
