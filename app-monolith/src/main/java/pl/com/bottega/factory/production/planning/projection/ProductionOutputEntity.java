package pl.com.bottega.factory.production.planning.projection;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity(name = "ProductionOutput")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductionOutputEntity {

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
        this.partsPerMinute = partsPerMinute;
        this.total = total;
    }
}
