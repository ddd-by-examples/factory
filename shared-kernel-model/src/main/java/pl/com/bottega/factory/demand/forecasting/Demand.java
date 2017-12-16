package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

@Value
public class Demand {
    private static final Demand NONE = of(0);

    long level;
    Schema schema;

    public enum Schema {
        AtDayStart, Every3hours, TillDayEnd, Twice
    }

    public static Demand of(long level) {
        return new Demand(level, Schema.TillDayEnd);
    }

    public static Demand of(long level, Schema schema) {
        return new Demand(level, schema);
    }

    static Demand nothingDemanded() {
        return NONE;
    }

    Demand nullIfNone() {
        return NONE == this ? null : this;
    }
}
