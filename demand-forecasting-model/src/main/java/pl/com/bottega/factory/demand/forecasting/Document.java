package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedMap;
import java.util.function.BiConsumer;

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

    public void forEachStartingFrom(LocalDate date, BiConsumer<LocalDate, Demand> f) {
        demands.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(date))
                .forEach(e -> f.accept(e.getKey(), e.getValue()));
    }
}

