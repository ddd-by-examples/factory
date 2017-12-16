package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.Demand;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "CurrentDemand")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CurrentDemandEntity implements Serializable {

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
    @Enumerated(EnumType.STRING)
    private Demand.Schema schema;

    CurrentDemandEntity(String refNo, LocalDate date, long level, Demand.Schema schema) {
        this.refNo = refNo;
        this.date = date;
        this.level = level;
        this.schema = schema;
    }
}
