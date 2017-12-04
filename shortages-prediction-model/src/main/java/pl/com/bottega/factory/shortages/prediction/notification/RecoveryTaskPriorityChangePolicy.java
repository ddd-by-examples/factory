package pl.com.bottega.factory.shortages.prediction.notification;

import pl.com.bottega.factory.shortages.prediction.Shortages;

import java.time.LocalDateTime;

/**
 * Created by michal on 18.05.2017.
 */
public interface RecoveryTaskPriorityChangePolicy {

    static RecoveryTaskPriorityChangePolicy never() {
        return (LocalDateTime now, Shortages shortage) -> false;
    }

    static RecoveryTaskPriorityChangePolicy onlyIn1DaysAhead() {
        return shortageInDays(1);
    }

    static RecoveryTaskPriorityChangePolicy shortageInDays(long shortageInXDays) {
        return (LocalDateTime now, Shortages shortage) ->
                shortage.getLockedParts() > 0 && shortage.anyBefore(
                        now.plusDays(shortageInXDays));
    }

    boolean shouldIncreasePriority(LocalDateTime now, Shortages shortage);
}
