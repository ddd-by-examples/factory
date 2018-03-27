package io.dddbyexamples.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
class ProductionOutputs {

    private final Map<LocalDateTime, Long> outputs;

    long getOutput(LocalDateTime from, LocalDateTime to) {
        if (!outputs.containsKey(from) || !outputs.containsKey(to)) {
            throw new IllegalArgumentException("No pre-calculated output for time " + to);
        }
        return outputs.get(to) - outputs.get(from);
    }
}
