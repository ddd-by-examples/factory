package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.ReviewRequested.ReviewNeeded;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.Clock;
import java.time.LocalDate;

@AllArgsConstructor
class ProductDemand {

    final RefNoId id;
    final Demands demands;

    final UnitOfWork unit;
    final Clock clock;
    final DemandEvents events;

    interface Demands {
        DailyDemand get(LocalDate date);
    }

    void adjust(AdjustDemand adjustDemand) {
        LocalDate today = LocalDate.now(clock);

        adjustDemand.forEachStartingFrom(today, this::adjustDaily);

        if (unit.anyChanges()) {
            events.emit(new DemandedLevelsChanged(id, unit.changes()));
        }
    }

    void process(Document document) {
        LocalDate today = LocalDate.now(clock);

        document.forEachStartingFrom(today, this::updateDaily);

        if (unit.anyChanges()) {
            events.emit(new DemandedLevelsChanged(id, unit.changes()));
        }
        if (unit.anyReviews()) {
            events.emit(new ReviewRequested(id, unit.reviews()));
        }
    }

    void review(ReviewNeeded review, ReviewDecision decision) {
        if (decision.requireAdjustment()) {
            adjust(decision.toAdjustment(review));
        }
    }

    private void adjustDaily(LocalDate date, Adjustment adjustment) {
        DailyDemand demand = demands.get(date);
        demand.adjust(adjustment);
    }

    private void updateDaily(LocalDate date, Demand demand) {
        DailyDemand daily = demands.get(date);
        daily.update(demand);
    }
}
