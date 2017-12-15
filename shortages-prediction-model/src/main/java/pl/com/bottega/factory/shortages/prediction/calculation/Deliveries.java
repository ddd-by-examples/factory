package pl.com.bottega.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
class Deliveries {

    private final Map<LocalDateTime, Long> forecast;

    long get(LocalDateTime time) {
        return forecast.getOrDefault(time, 0L);
    }
}
