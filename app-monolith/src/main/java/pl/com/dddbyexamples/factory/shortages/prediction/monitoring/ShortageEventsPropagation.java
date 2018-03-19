package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.com.dddbyexamples.factory.shortages.prediction.notification.NotificationOfShortage;

@Lazy
@Component
@AllArgsConstructor
class ShortageEventsPropagation implements ShortageEvents {

    private final NotificationOfShortage notification;

    @Override
    public void emit(NewShortage event) {
        notification.notifyAbout(event);
    }

    @Override
    public void emit(ShortageSolved event) {

    }
}
