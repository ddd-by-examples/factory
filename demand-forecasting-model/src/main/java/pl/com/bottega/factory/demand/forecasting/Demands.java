package pl.com.bottega.factory.demand.forecasting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

class Demands implements ProductDemand.Demands, DailyDemand.Events {
    final Map<LocalDate, DailyDemand> fetched = new HashMap<>();
    final List<DailyDemand.LevelChanged> changes = new ArrayList<>();
    final List<DailyDemand.DemandUpdated> updates = new ArrayList<>();
    final List<DailyDemand.ReviewRequest> warnings = new ArrayList<>();
    Function<LocalDate, DailyDemand> fetch;

    @Override
    public DailyDemand get(LocalDate date) {
        return fetched.computeIfAbsent(date, fetch);
    }

    @Override
    public List<DailyDemand.LevelChanged> getChanges() {
        return unmodifiableList(changes);
    }

    @Override
    public List<DailyDemand.DemandUpdated> getUpdates() {
        return unmodifiableList(updates);
    }

    @Override
    public List<DailyDemand.ReviewRequest> getReviewRequests() {
        return unmodifiableList(warnings);
    }

    @Override
    public void emit(DailyDemand.LevelChanged event) {
        changes.add(event);
    }

    @Override
    public void emit(DailyDemand.ReviewRequest event) {
        warnings.add(event);
    }

    @Override
    public void emit(DailyDemand.DemandUpdated event) {
        updates.add(event);
    }

    @Override
    public boolean anyChanges() {
        return !getChanges().isEmpty();
    }

    @Override
    public Map<DailyId, DemandEvents.DemandedLevelsChanged.Change> changes() {
        return getChanges().stream().collect(Collectors.toMap(
                DailyDemand.LevelChanged::getId, DailyDemand.LevelChanged::getChange));
    }
}
