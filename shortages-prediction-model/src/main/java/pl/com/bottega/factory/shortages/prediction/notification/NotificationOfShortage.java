package pl.com.bottega.factory.shortages.prediction.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.com.bottega.factory.shortages.prediction.Shortages;
import pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage;
import pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage.After;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
public class NotificationOfShortage {

    private final QualityTasks qualityTasks;
    private final Clock clock;

    private final RecoveryTaskPriorityChangePolicy policy;
    private final NotificationRules rules;

    static NotificationRules rulesOfPlannerNotification(Notifications notifications) {
        return NotificationRules.builder()
                .rule(After.DemandChanged, notifications::alertPlanner)
                .rule(After.PlanChanged, notifications::markOnPlan)
                .rule(After.StockChanged, notifications::alertPlanner)
                .rule(After.LockedParts, notifications::softNotifyPlanner)
                .otherwise(notifications::alertPlanner)
                .build();
    }

    public void notifyAbout(NewShortage event) {
        Shortages shortage = event.getShortages();
        rules.wayOfNotificationAfter(event.getTrigger())
                .notifyAbout(event.getShortages());

        if (policy.shouldIncreasePriority(LocalDateTime.now(clock), shortage)) {
            qualityTasks.increasePriorityFor(shortage.getRefNo());
        }
    }

    @Value
    @Builder
    static class NotificationRules {
        @Singular
        Map<After, Notificator> rules;
        Notificator otherwise;

        Notificator wayOfNotificationAfter(After trigger) {
            return rules.getOrDefault(trigger, otherwise);
        }
    }

    interface Notificator {
        void notifyAbout(Shortages shortages);
    }
}
