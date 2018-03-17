package pl.com.dddbyexamples.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged.Change;
import pl.com.dddbyexamples.factory.product.management.RefNoId;

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

        List<DailyDemand.Result> results = adjustDemand
                .forEachStartingFrom(today, this::adjustDaily);
        updates.addAll(DailyDemand.Result.updates(results));

        Map<DailyId, Change> changes = DailyDemand.Result.levelChanges(results);

        if (!changes.isEmpty()) {
            events.emit(new DemandedLevelsChanged(id, changes));
        }
    }

    void process(Document document) {
        LocalDate today = LocalDate.now(clock);

        List<DailyDemand.Result> results = document
                .forEachStartingFrom(today, this::updateDaily);
        updates.addAll(DailyDemand.Result.updates(results));

        Map<DailyId, Change> changes = DailyDemand.Result.levelChanges(results);

        if (!changes.isEmpty()) {
            events.emit(new DemandedLevelsChanged(id, changes));
        }

        List<ReviewRequired.ToReview> reviews = DailyDemand.Result.reviews(results);

        if (!reviews.isEmpty()) {
            events.emit(new ReviewRequired(id, reviews));
        }
    }

    void review(ApplyReviewDecision reviewDecision) {
        if (reviewDecision.requireAdjustment()) {
            adjust(reviewDecision.toAdjustment());
        }
    }

    private DailyDemand.Result adjustDaily(LocalDate date, Adjustment adjustment) {
        DailyDemand demand = demands.get(date);
        return demand.adjust(adjustment);
    }

    private DailyDemand.Result updateDaily(LocalDate date, Demand demand) {
        DailyDemand daily = demands.get(date);
        return daily.update(demand);
    }
}
