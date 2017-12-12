package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastProjection;
import pl.com.bottega.factory.demand.forecasting.command.DemandReviewDao;
import pl.com.bottega.factory.demand.forecasting.command.DemandReviewEntity;
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandProjection;
import pl.com.bottega.factory.shortages.prediction.ShortagePredictionEventsMapping;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.Collectors;

@Lazy
@Component
@AllArgsConstructor
class DemandEventsMapping implements DemandEvents {

    private final CurrentDemandProjection demands;
    private final DeliveryForecastProjection deliveries;
    private final ShortagePredictionEventsMapping predictions;
    private final DemandReviewDao reviews;
    private final Clock clock;

    @Override
    public void emit(DemandedLevelsChanged event) {
        demands.persistCurrentDemands(event);
        deliveries.persistDeliveryForecasts(event);
        predictions.predictShortages(event);
    }

    @Override
    public void emit(ReviewRequested event) {
        reviews.save(event.getReviews().stream()
                .map(review -> new DemandReviewEntity(Instant.now(clock), review))
                .collect(Collectors.toList())
        );
    }
}
