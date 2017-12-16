package pl.com.bottega.factory.production.planning.projection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity(name = "ProductionOutput")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "refNo")
public class ProductionOutputEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String refNo;
    @Column
    private LocalDateTime start;
    @Column
    private Duration duration;
    @Column
    private LocalDateTime end;
    @Column
    private int partsPerMinute;
    @Column
    private long total;

    public ProductionOutputEntity(String refNo,
                                  LocalDateTime start, Duration duration,
                                  int partsPerMinute,
                                  long total) {
        this.refNo = refNo;
        this.start = start;
        this.duration = duration;
        this.end = start.plus(duration);
        this.partsPerMinute = partsPerMinute;
        this.total = total;
    }
}
