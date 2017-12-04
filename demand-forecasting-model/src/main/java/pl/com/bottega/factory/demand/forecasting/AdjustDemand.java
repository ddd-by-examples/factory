package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;

@Value
public class AdjustDemand {
    String refNo;
    Map<LocalDate, Adjustment> adjustments;

    public AdjustDemand(String refNo,
                        Map<LocalDate, Adjustment> adjustments) {
        this.refNo = refNo;
        this.adjustments = Collections.unmodifiableMap(adjustments);
    }

    public void forEachStartingFrom(LocalDate date, BiConsumer<LocalDate, Adjustment> f) {
        adjustments.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(date))
                .forEach(e -> f.accept(e.getKey(), e.getValue()));
    }
}
