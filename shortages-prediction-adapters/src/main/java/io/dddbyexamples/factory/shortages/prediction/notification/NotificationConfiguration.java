package io.dddbyexamples.factory.shortages.prediction.notification;

import io.dddbyexamples.factory.shortages.prediction.Shortage;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@AllArgsConstructor
public class NotificationConfiguration {

    private final Clock clock;
    private final QualityTasks qualityTasks = new MockedJiraIntegration();
    private final Notifications notifications = new MockedPlannerPushNotifications();
    private final RecoveryTaskPriorityChangePolicy policy =
            RecoveryTaskPriorityChangePolicy.shortageInDays(2);

    @Bean
    public NotificationOfShortage notificationOfShortage() {
        return new NotificationOfShortage(
                qualityTasks, clock, policy,
                NotificationOfShortage.rulesOfPlannerNotification(notifications)
        );
    }

    private static class MockedPlannerPushNotifications implements Notifications {
        @Override
        public void alertPlanner(Shortage shortage) {

        }

        @Override
        public void softNotifyPlanner(Shortage shortage) {

        }

        @Override
        public void markOnPlan(Shortage shortage) {

        }
    }

    private class MockedJiraIntegration implements QualityTasks {
        @Override
        public void increasePriorityFor(String productRefNo) {

        }
    }
}
