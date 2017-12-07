package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;
import pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged.Change;

import java.util.Objects;
import java.util.Optional;

class DailyDemand {

    private final DailyId id;
    private final Events events;

    private Demand documented;
    private Adjustment adjustment;

    interface Events {
        void emit(LevelChanged event);

        void emit(ReviewRequest event);

        void emit(DemandUpdated event);
    }

    DailyDemand(DailyId id, Events events,
                Demand documented, Adjustment adjustment) {
        this.id = id;
        this.events = events;
        this.documented = Optional.ofNullable(documented)
                .orElse(Demand.nothingDemanded());
        this.adjustment = adjustment;
    }

    void adjust(Adjustment adjustment) {
        State state = state();
        this.adjustment = adjustment;

        if (state.updated()) {
            events.emit(new DemandUpdated(id, documented, adjustment));
        }
        if (state.levelChanged()) {
            events.emit(new LevelChanged(id, state.getLevelChange()));
        }
    }

    void update(Demand documented) {
        State state = state();
        if (!Adjustment.isStrong(this.adjustment)) {
            this.adjustment = null;
        }
        this.documented = documented;

        if (state.updated()) {
            events.emit(new DemandUpdated(id, documented, adjustment));
        }
        if (state.levelChanged()) {
            events.emit(new LevelChanged(id, state.getLevelChange()));
        }
    }

    Demand getLevel() {
        return Optional.ofNullable(adjustment)
                .map(Adjustment::getDemand)
                .orElse(documented);
    }

    @Value
    static class LevelChanged {
        DailyId id;
        Change change;
    }

    @Value
    static class ReviewRequest {
        DailyId id;
        Demand previousDocumented;
        Demand strongAdjustment;
        Demand newDocumented;
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
}
