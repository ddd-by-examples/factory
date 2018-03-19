package pl.com.dddbyexamples.factory.shortages.prediction.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage;
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.NewShortage;

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
                .rule(NewShortage.After.DemandChanged, notifications::alertPlanner)
                .rule(NewShortage.After.PlanChanged, notifications::markOnPlan)
                .rule(NewShortage.After.StockChanged, notifications::alertPlanner)
                .rule(NewShortage.After.LockedParts, notifications::softNotifyPlanner)
                .otherwise(notifications::alertPlanner)
                .build();
    }

    public void notifyAbout(NewShortage event) {
        Shortage shortage = event.getShortage();
        rules.wayOfNotificationAfter(event.getTrigger())
                .notifyAbout(event.getShortage());

        if (policy.shouldIncreasePriority(LocalDateTime.now(clock), shortage)) {
            qualityTasks.increasePriorityFor(shortage.getRefNo());
        }
    }

    @Value
    @Builder
    static class NotificationRules {
        @Singular
        Map<NewShortage.After, Notificator> rules;
        Notificator otherwise;

        Notificator wayOfNotificationAfter(NewShortage.After trigger) {
            return rules.getOrDefault(trigger, otherwise);
        }
    }

    interface Notificator {
        void notifyAbout(Shortage shortage);
    }
}
