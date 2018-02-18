package pl.com.bottega.factory.demand.forecasting;

import lombok.Builder;
import lombok.Value;
import pl.com.bottega.factory.demand.forecasting.DemandedLevelsChanged.Change;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;

import java.util.*;
import java.util.stream.Collectors;

class DailyDemand {

    private final DailyId id;
    private Demand documented;
    private Adjustment adjustment;

    private final ReviewPolicy policy;

    DailyDemand(DailyId id, ReviewPolicy policy,
                Demand documented, Adjustment adjustment) {
        this.id = id;
        this.policy = policy;
        this.documented = Optional.ofNullable(documented)
                .orElse(Demand.nothingDemanded());
        this.adjustment = adjustment;
    }

    Result adjust(Adjustment adjustment) {
        Result.ResultBuilder result = Result.builder(id);
        State state = state();
        this.adjustment = adjustment;

        if (state.updated()) {
            result.updated(new DemandUpdated(id, documented, adjustment));
        }
        if (state.levelChanged()) {
            result.levelChange(state.getLevelChange());
        }
        return result.build();
    }

    Result update(Demand documented) {
        Result.ResultBuilder result = Result.builder(id);
        State state = state();
        if (policy.reviewNeeded(this.documented, this.adjustment, documented)) {
            result.toReview(new ToReview(id,
                    this.documented,
                    this.adjustment.getDemand(),
                    documented)
            );
        }
        if (Adjustment.isNotStrong(this.adjustment)) {
            this.adjustment = null;
        }
        this.documented = documented;

        if (state.updated()) {
            result.updated(new DemandUpdated(id, documented, adjustment));
        }
        if (state.levelChanged()) {
            result.levelChange(state.getLevelChange());
        }
        return result.build();
    }

    Demand getLevel() {
        return Optional.ofNullable(adjustment)
                .map(Adjustment::getDemand)
                .orElse(documented);
    }

    @Value
    static class DemandUpdated {
        DailyId id;
        Demand documented;
        Adjustment adjustment;
    }

    private State state() {
        return new State();
    }

    private class State {
        final Demand documented;
        final Adjustment adjustment;
        final Demand level;

        State() {
            this.documented = DailyDemand.this.documented;
            this.adjustment = DailyDemand.this.adjustment;
            this.level = getLevel();
        }

        boolean updated() {
            return !Objects.equals(this.documented, DailyDemand.this.documented)
                    || !Objects.equals(this.adjustment, DailyDemand.this.adjustment);
        }

        Change getLevelChange() {
            return new Change(level, getLevel());
        }

        boolean levelChanged() {
            return !level.equals(getLevel());
        }
    }

    @Builder
    @Value
    static class Result {
        DailyId id;
        DemandUpdated updated;
        DemandedLevelsChanged.Change levelChange;
        ReviewRequired.ToReview toReview;

        static ResultBuilder builder(DailyId id) {
            return new ResultBuilder().id(id);
        }

        static List<ToReview> reviews(List<Result> results) {
            return Collections.unmodifiableList(results.stream()
                    .filter(result -> result.toReview != null)
                    .map(result -> result.toReview)
                    .collect(Collectors.toList()));
        }

        static Map<DailyId, Change> levelChanges(List<Result> results) {
            return Collections.unmodifiableMap(results.stream()
                    .filter(result -> result.levelChange != null)
                    .collect(Collectors.toMap(
                            result -> result.id,
                            result -> result.levelChange
                    )));
        }

        static List<DemandUpdated> updates(List<Result> results) {
            return Collections.unmodifiableList(results.stream()
                    .filter(result -> result.updated != null)
                    .map(result -> result.updated)
                    .collect(Collectors.toList()));
        }
    }
}
