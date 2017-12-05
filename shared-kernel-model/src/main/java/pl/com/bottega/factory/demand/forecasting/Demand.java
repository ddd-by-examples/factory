package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

@Value
public class Demand {
    long level;
    Schema schema;

    public enum Schema {
        AtDayStart, Every3hours, TillDayEnd, Twice
    }

    public static Demand nothingDemanded() {
        return of(0);
    }

    public static Demand of(long level) {
        return new Demand(level, Schema.TillDayEnd);
    }

    public static Demand of(long level, Schema schema) {
        return new Demand(level, schema);
    }
}
