package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.LocalDate;
import java.util.List;

@Value
public class ReviewRequired {
    RefNoId refNo;
    List<ToReview> reviews;

    @Value
    public static class ToReview {
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
