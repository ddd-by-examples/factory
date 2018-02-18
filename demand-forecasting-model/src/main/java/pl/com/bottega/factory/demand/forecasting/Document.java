package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Value
public class Document {

    Instant created;
    String refNo;
    SortedMap<LocalDate, Demand> demands;

    public Document(Instant created, String refNo, SortedMap<LocalDate, Demand> demands) {
        this.created = created;
        this.refNo = refNo;
        this.demands = Collections.unmodifiableSortedMap(demands);
    }

    public List<DailyDemand.Result> forEachStartingFrom(LocalDate date, BiFunction<LocalDate, Demand, DailyDemand.Result> f) {
        return demands.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(date))
                .map(e -> f.apply(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}

