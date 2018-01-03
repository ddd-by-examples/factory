package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastProjection;
import pl.com.bottega.factory.demand.forecasting.command.RequiredReviewDao;
import pl.com.bottega.factory.demand.forecasting.command.RequiredReviewEntity;
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandProjection;
import pl.com.bottega.factory.shortages.prediction.monitoring.ShortagePredictionService;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.Collectors;

@Lazy
@Component
@AllArgsConstructor
class DemandEventsMapping implements DemandEvents {

    private final CurrentDemandProjection demandProjection;
    private final DeliveryForecastProjection deliveryProjection;
    private final ShortagePredictionService shortagePrediction;
    private final RequiredReviewDao demandReviews;
    private final Clock clock;

    @Override
    public void emit(DemandedLevelsChanged event) {
        demandProjection.persistCurrentDemands(event);
        deliveryProjection.persistDeliveryForecasts(event);
        shortagePrediction.predictShortages(event);
    }

    @Override
    public void emit(ReviewRequired event) {
        Instant timestamp = Instant.now(clock);
        demandReviews.save(event.getReviews().stream()
                .map(r -> new RequiredReviewEntity(timestamp, r))
                .collect(Collectors.toList())
        );
    }
}
