package io.dddbyexamples.factory.shortages.prediction.notification;

import io.dddbyexamples.factory.shortages.prediction.Shortage;

import java.time.LocalDateTime;

/**
 * Created by michal on 18.05.2017.
 */
public interface RecoveryTaskPriorityChangePolicy {

    static RecoveryTaskPriorityChangePolicy never() {
        return (LocalDateTime now, Shortage shortage) -> false;
    }

    static RecoveryTaskPriorityChangePolicy onlyIn1DaysAhead() {
        return shortageInDays(1);
    }

    static RecoveryTaskPriorityChangePolicy shortageInDays(long shortageInXDays) {
        return (LocalDateTime now, Shortage shortage) ->
                shortage.getLockedParts() > 0 && shortage.anyBefore(
                        now.plusDays(shortageInXDays));
    }

    boolean shouldIncreasePriority(LocalDateTime now, Shortage shortage);
}
