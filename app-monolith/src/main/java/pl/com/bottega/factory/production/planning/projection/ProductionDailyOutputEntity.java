package pl.com.bottega.factory.production.planning.projection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "ProductionDailyOutput")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductionDailyOutputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String refNo;
    @Column
    private LocalDate date;
    @Column
    private long output;

    public ProductionDailyOutputEntity(String refNo, LocalDate date, long output) {
        this.refNo = refNo;
        this.date = date;
        this.output = output;
    }
}
