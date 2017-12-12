package pl.com.bottega.factory.demand.forecasting;

import pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged;
import pl.com.bottega.factory.demand.forecasting.DemandEvents.ReviewRequested.ReviewNeeded;

import java.util.*;

import static java.util.Collections.unmodifiableList;

class UnitOfWork implements DailyDemand.Events {

    Map<DailyId, DemandedLevelsChanged.Change> changes = new HashMap<>();
    List<ReviewNeeded> reviews = new LinkedList<>();
    List<DailyDemand.DemandUpdated> updates = new LinkedList<>();

    boolean anyChanges() {
        return !changes.isEmpty();
    }

    Map<DailyId, DemandedLevelsChanged.Change> changes() {
        return Collections.unmodifiableMap(changes);
    }

    boolean anyReviews() {
        return !reviews.isEmpty();
    }

    List<ReviewNeeded> reviews() {
        return Collections.unmodifiableList(reviews);
    }

    List<DailyDemand.DemandUpdated> updates() {
        return unmodifiableList(updates);
    }

    @Override
    public void emit(DailyDemand.LevelChanged event) {
        changes.put(event.getId(), event.getChange());
    }

    @Override
    public void emit(ReviewNeeded event) {
        reviews.add(event);
    }

    @Override
    public void emit(DailyDemand.DemandUpdated event) {
        updates.add(event);
    }
}
