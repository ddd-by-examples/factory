package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.Result;
import pl.com.bottega.factory.demand.forecasting.DemandedLevelsChanged.Change;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
class ProductDemand {

    final RefNoId id;
    final List<DailyDemand.DemandUpdated> updates;
    private final Demands demands;

    private final Clock clock;
    private final DemandEvents events;

    interface Demands {
        DailyDemand get(LocalDate date);
    }

    void adjust(AdjustDemand adjustDemand) {
        LocalDate today = LocalDate.now(clock);

        List<Result> results = adjustDemand
                .forEachStartingFrom(today, this::adjustDaily);
        updates.addAll(Result.updates(results));

        Map<DailyId, Change> changes = Result.levelChanges(results);

        if (!changes.isEmpty()) {
            events.emit(new DemandedLevelsChanged(id, changes));
        }
    }

    void process(Document document) {
        LocalDate today = LocalDate.now(clock);

        List<Result> results = document
                .forEachStartingFrom(today, this::updateDaily);
        updates.addAll(Result.updates(results));

        Map<DailyId, Change> changes = Result.levelChanges(results);

        if (!changes.isEmpty()) {
            events.emit(new DemandedLevelsChanged(id, changes));
        }

        List<ToReview> reviews = Result.reviews(results);

        if (!reviews.isEmpty()) {
            events.emit(new ReviewRequired(id, reviews));
        }
    }

    void review(ToReview review, ReviewDecision decision) {
        if (decision.requireAdjustment()) {
            adjust(decision.toAdjustment(review));
        }
    }

    private Result adjustDaily(LocalDate date, Adjustment adjustment) {
        DailyDemand demand = demands.get(date);
        return demand.adjust(adjustment);
    }

    private Result updateDaily(LocalDate date, Demand demand) {
        DailyDemand daily = demands.get(date);
        return daily.update(demand);
    }
}
