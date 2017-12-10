package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

@Value
public class AdjustDemand {
    String refNo;
    Map<LocalDate, Adjustment> adjustments;

    public void forEachStartingFrom(LocalDate date, BiConsumer<LocalDate, Adjustment> f) {
        adjustments.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(date))
                .forEach(e -> f.accept(e.getKey(), e.getValue()));
    }

    public Optional<LocalDate> latestAdjustment() {
        return adjustments.keySet().stream().max(Comparator.naturalOrder());
    }
}
