package pl.com.bottega.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.shortages.prediction.Configuration;
import pl.com.bottega.factory.shortages.prediction.Shortages;
import pl.com.bottega.factory.shortages.prediction.calculation.Forecasts;
import pl.com.bottega.factory.shortages.prediction.notification.NotificationOfShortage;

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
class ShortagePredictionProcessORMRepository implements ShortagePredictionProcessRepository {

    private final ShortagesDao dao;
    private final ShortageDiffPolicy policy = ShortageDiffPolicy.ValuesAreEquals;
    private final Forecasts forecasts;
    private final Configuration configuration = () -> 14;
    private final NotificationOfShortage notifications;

    @Override
    public ShortagePredictionProcess get(String refNo) {
        return new ShortagePredictionProcess(
                refNo, fetchData(refNo),
                policy, forecasts, configuration, new EventsHandler()
        );
    }

    @Override
    public void save(ShortagePredictionProcess model) {
        // persisted after event
    }

    private Shortages fetchData(String refNo) {
        return ofNullable(dao.findOne(refNo))
                .map(ShortagesEntity::getShortages).orElse(null);
    }

    private class EventsHandler implements ShortageEvents {
        @Override
        public void emit(NewShortage event) {
            String refNo = event.getShortages().getRefNo();
            ShortagesEntity entity = ofNullable(dao.findOne(refNo))
                    .orElseGet(() -> new ShortagesEntity(refNo));
            entity.setShortages(event.getShortages());
            dao.save(entity);
            notifications.emit(event);
        }

        @Override
        public void emit(ShortageSolved event) {
            dao.delete(event.getRefNo());
            notifications.emit(event);
        }
    }
}
