package pl.com.bottega.factory.demand.forecasting;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastProjection;
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandProjection;
import pl.com.bottega.factory.shortages.prediction.ShortagePredictionEventsMapping;

@Lazy
@Component
class DemandEventsMapping implements DemandEvents {

    CurrentDemandProjection demands;
    DeliveryForecastProjection deliveries;
    ShortagePredictionEventsMapping predictions;

    @Override
    public void emit(DemandedLevelsChanged event) {
        demands.emit(event);
        deliveries.emit(event);
        predictions.emit(event);
    }
}
