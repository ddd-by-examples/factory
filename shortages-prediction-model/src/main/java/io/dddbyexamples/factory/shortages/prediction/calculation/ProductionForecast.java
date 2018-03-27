package io.dddbyexamples.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
class ProductionForecast {
    List<Item> items;

    ProductionOutputs outputsInTimes(LocalDateTime now, Set<LocalDateTime> times) {
        return new ProductionOutputs(
                (times.contains(now) ? times.stream() : Stream.concat(Stream.of(now), times.stream()))
                        .parallel()
                        .collect(Collectors.toMap(
                                Function.identity(),
                                time -> items.parallelStream()
                                        .mapToLong(item -> item.partsAt(time))
                                        .sum()
                        ))
        );
    }

    @AllArgsConstructor
    static class Item {
        final LocalDateTime start;
        final Duration duration;
        final int partsPerMinute;

        long partsAt(LocalDateTime time) {
            if (startsAfter(time)) {
                return 0;
            }
            if (endsBefore(time)) {
                return duration.toMinutes() * partsPerMinute;
            }
            return Duration.between(start, time).getSeconds() * partsPerMinute / 60;
        }

        boolean startsAfter(LocalDateTime time) {
            return start.isAfter(time);
        }

        boolean endsBefore(LocalDateTime time) {
            return start.plus(duration).isBefore(time);
        }
    }
}
