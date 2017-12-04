package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryForecastEntity {
    private final String refNo;
    private final LocalDateTime time;
    private final long level;

    public DeliveryForecastEntity(String refNo, LocalDateTime time, long level) {
        this.refNo = refNo;
        this.time = time;
        this.level = level;
    }
}
