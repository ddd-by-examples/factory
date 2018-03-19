package pl.com.dddbyexamples.factory.production.planning.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity(name = "ProductionOutput")
@Table(schema = "production_planning")
@Getter
@NoArgsConstructor
public class ProductionOutputEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    private LocalDateTime start;
    private long duration;
    private LocalDateTime end;
    private int partsPerMinute;
    private long total;

    ProductionOutputEntity(String refNo,
                           LocalDateTime start, Duration duration,
                           int partsPerMinute,
                           long total) {
        this.refNo = refNo;
        this.start = start;
        this.duration = duration.getSeconds();
        this.end = start.plus(duration);
        this.partsPerMinute = partsPerMinute;
        this.total = total;
    }

    public Duration getDuration() {
        return Duration.ofSeconds(duration);
    }
}
