package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.Demand;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "CurrentDemand")
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

    public CurrentDemandEntity(String refNo, LocalDate date, long level, Demand.Schema schema) {
        this.refNo = refNo;
        this.date = date;
        this.level = level;
        this.schema = schema;
    }
}
