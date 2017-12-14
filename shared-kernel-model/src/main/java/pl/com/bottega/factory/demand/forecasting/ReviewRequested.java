package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Value
public class ReviewRequested {
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
