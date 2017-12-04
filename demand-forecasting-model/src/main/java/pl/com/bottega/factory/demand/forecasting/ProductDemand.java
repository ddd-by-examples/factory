package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.DemandUpdated;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.LevelChanged;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.ReviewRequest;
import pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged;
import pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged.Change;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
class ProductDemand {

    final RefNoId id;
    final Demands demands;

    final Clock clock;
    final DemandEvents events;

    interface Demands {
        DailyDemand get(LocalDate date);

        List<LevelChanged> getChanges();

        List<DemandUpdated> getUpdates();

        List<ReviewRequest> getReviewRequests();

        boolean anyChanges();

        Map<DailyId, Change> changes();
    }

    void adjust(AdjustDemand adjustDemand) {
        LocalDate today = LocalDate.now(clock);

        adjustDemand.forEachStartingFrom(today, this::adjustDaily);

        if (demands.anyChanges()) {
            events.emit(new DemandedLevelsChanged(id, demands.changes()));
        }
    }

    void process(Document document) {
        LocalDate today = LocalDate.now(clock);

        document.forEachStartingFrom(today, this::updateDaily);

        if (demands.anyChanges()) {
            events.emit(new DemandedLevelsChanged(id, demands.changes()));
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
