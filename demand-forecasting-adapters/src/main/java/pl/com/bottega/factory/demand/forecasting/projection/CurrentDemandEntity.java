package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.Demand;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "CurrentDemand")
@Table(schema = "demand_forecasting")
@Getter
@NoArgsConstructor
public class CurrentDemandEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    private LocalDate date;
    private long level;
    @Enumerated(EnumType.STRING)
    private Demand.Schema schema;

    CurrentDemandEntity(String refNo, LocalDate date, long level, Demand.Schema schema) {
        this.refNo = refNo;
        this.date = date;
        this.level = level;
        this.schema = schema;
    }

    void changeLevelTo(long level, Demand.Schema schema) {
        this.level = level;
        this.schema = schema;
    }
}
