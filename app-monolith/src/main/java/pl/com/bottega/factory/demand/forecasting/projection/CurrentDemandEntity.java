package pl.com.bottega.factory.demand.forecasting.projection;

import pl.com.bottega.factory.demand.forecasting.Demand;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrentDemandEntity {
    private final String refNo;
    private final LocalDate date;
    private final long level;
    private final Demand.Schema schema;

    public CurrentDemandEntity(String refNo, LocalDate date, long level, Demand.Schema schema) {
        this.refNo = refNo;
        this.date = date;
        this.level = level;
        this.schema = schema;
    }
}
