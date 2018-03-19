package pl.com.dddbyexamples.factory.production.planning.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "ProductionDailyOutput")
@Table(schema = "production_planning")
@Getter
@NoArgsConstructor
public class ProductionDailyOutputEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    private LocalDate date;
    private long output;

    ProductionDailyOutputEntity(String refNo, LocalDate date, long output) {
        this.refNo = refNo;
        this.date = date;
        this.output = output;
    }
}
