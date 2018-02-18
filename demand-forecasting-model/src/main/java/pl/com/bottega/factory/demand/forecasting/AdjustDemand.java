package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.Result;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Value
public class AdjustDemand {
    String refNo;
    Map<LocalDate, Adjustment> adjustments;

    public List<Result> forEachStartingFrom(LocalDate date, BiFunction<LocalDate, Adjustment, Result> f) {
        return adjustments.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(date))
                .map(e -> f.apply(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public Optional<LocalDate> latestAdjustment() {
        return adjustments.keySet().stream().max(Comparator.naturalOrder());
    }
}
