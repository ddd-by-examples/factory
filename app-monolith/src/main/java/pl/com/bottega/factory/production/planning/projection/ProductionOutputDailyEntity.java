package pl.com.bottega.factory.production.planning.projection;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductionOutputDailyEntity {
    private final String refNo;
    private final LocalDate date;
    private final long level;
}
