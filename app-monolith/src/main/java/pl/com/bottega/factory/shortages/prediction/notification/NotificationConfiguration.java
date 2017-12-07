package pl.com.bottega.factory.shortages.prediction.notification;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.bottega.factory.shortages.prediction.Shortages;

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
        public void alertPlanner(Shortages shortage) {

        }

        @Override
        public void softNotifyPlanner(Shortages shortage) {

        }

        @Override
        public void markOnPlan(Shortages shortage) {

        }
    }

    private class MockedJiraIntegration implements QualityTasks {
        @Override
        public void increasePriorityFor(String productRefNo) {

        }
    }
}
