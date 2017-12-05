package pl.com.bottega.factory.production.planning.projection;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class ProductionOutputEntity {
    private final String refNo;
    private final LocalDateTime start;
    private final Duration duration;
    private final int partsPerMinute;
    private final long total;
}
