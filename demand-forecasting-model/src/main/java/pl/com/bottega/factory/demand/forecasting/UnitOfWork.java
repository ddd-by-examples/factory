package pl.com.bottega.factory.demand.forecasting;

import java.util.*;

import static java.util.Collections.unmodifiableList;

class UnitOfWork implements DailyDemand.Events {

    Map<DailyId, DemandEvents.DemandedLevelsChanged.Change> changes = new HashMap<>();
    List<DailyDemand.ReviewRequest> reviews = new LinkedList<>();
    List<DailyDemand.DemandUpdated> updates = new LinkedList<>();

    boolean anyChanges() {
        return !changes.isEmpty();
    }

    Map<DailyId, DemandEvents.DemandedLevelsChanged.Change> changes() {
        return Collections.unmodifiableMap(changes);
    }

    boolean anyReviews() {
        return !reviews.isEmpty();
    }

    List<DailyDemand.ReviewRequest> reviews() {
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
    public void emit(DailyDemand.ReviewRequest event) {
        reviews.add(event);
    }

    @Override
    public void emit(DailyDemand.DemandUpdated event) {
        updates.add(event);
    }
}
