package pl.com.dddbyexamples.factory.shortages.prediction.calculation

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeGrammar {
    static getDays(Integer self) { Duration.ofDays self }

    static getDay(Integer self) { Duration.ofDays self }

    static getH(Integer self) { Duration.ofHours self }

    static getMin(Integer self) { Duration.ofMinutes self }

    static String toString(LocalDateTime self, String pattern) { self.format(DateTimeFormatter.ofPattern(pattern)) }

    static apply() {
        Integer.mixin(TimeGrammar)
    }
}
