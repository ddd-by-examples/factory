package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastProjection;
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandProjection;
import pl.com.bottega.factory.shortages.prediction.ShortagePredictionEventsMapping;

@Lazy
@Component
@AllArgsConstructor
class DemandEventsMapping implements DemandEvents {

    private final CurrentDemandProjection demands;
    private final DeliveryForecastProjection deliveries;
    private final ShortagePredictionEventsMapping predictions;

    @Override
    public void emit(DemandedLevelsChanged event) {
        demands.emit(event);
        deliveries.emit(event);
        predictions.emit(event);
    }
}
