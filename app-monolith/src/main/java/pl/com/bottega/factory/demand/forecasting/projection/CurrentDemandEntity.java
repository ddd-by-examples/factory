package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.*;
import pl.com.bottega.factory.demand.forecasting.Demand;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "CurrentDemand")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CurrentDemandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String refNo;
    @Column
    private LocalDate date;
    @Column
    private long level;
    @Column
    private Demand.Schema schema;

    CurrentDemandEntity(String refNo, LocalDate date, long level, Demand.Schema schema) {
        this.refNo = refNo;
        this.date = date;
        this.level = level;
        this.schema = schema;
    }
}
