package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.Result;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AdjustDemand {
    private final String refNo;
    private final Map<LocalDate, Adjustment> adjustments;

    List<Result> forEachStartingFrom(LocalDate date, BiFunction<LocalDate, Adjustment, Result> f) {
        return adjustments.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(date))
                .map(e -> f.apply(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public Optional<LocalDate> latestAdjustment() {
        return adjustments.keySet().stream()
                .max(Comparator.naturalOrder());
    }

    public String getRefNo() {
        return refNo;
    }
}
