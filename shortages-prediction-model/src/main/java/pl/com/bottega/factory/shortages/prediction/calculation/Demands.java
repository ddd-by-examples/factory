package pl.com.bottega.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
class Demands {

    private final Map<LocalDateTime, Long> demands;

    long get(LocalDateTime time) {
        return demands.getOrDefault(time, 0L);
    }
}
