package pl.com.dddbyexamples.factory.shortages.prediction;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Levels missing to satisfy customer demand of particular product.
 * <p>
 * Created by michal on 22.10.2015.
 */
@Value
public class Shortage {

    private final String refNo;
    private final long lockedParts;
    private final LocalDateTime found;
    private final SortedMap<LocalDateTime, Long> shortages;

    public static Shortage.Builder builder(String refNo, long locked, LocalDateTime found) {
        return new Builder(refNo, locked, found);
    }

    public static boolean areNotSame(Shortage first, Shortage second) {
        return !areSame(first, second);
    }

    public static boolean areSame(Shortage first, Shortage second) {
        boolean noShortages = first == null && second == null;
        boolean onlyOne = first == null && second != null || first != null && second == null;
        if (noShortages || onlyOne) return false;
        boolean sameProduct = first.refNo.equals(second.refNo);
        boolean sameNumbers = first.shortages.equals(second.shortages);
        return sameProduct && sameNumbers;
    }

    public boolean anyBefore(LocalDateTime time) {
        return shortages.firstKey().isBefore(time);
    }

    @AllArgsConstructor
    public static class Builder {
        private final String refNo;
        private final long locked;
        private final LocalDateTime found;
        private final SortedMap<LocalDateTime, Long> gaps = new TreeMap<>();

        public Builder missing(LocalDateTime time, long level) {
            gaps.put(time, Math.abs(level));
            return this;
        }

        public Optional<Shortage> build() {
            if (gaps.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(new Shortage(refNo, locked, found,
                        Collections.unmodifiableSortedMap(gaps)));
            }
        }
    }
}
