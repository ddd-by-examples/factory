package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "DeliveryForecast")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DeliveryForecastEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String refNo;
    @Column
    private LocalDate date;
    @Column
    private LocalDateTime time;
    @Column
    private long level;

    public DeliveryForecastEntity(String refNo, LocalDateTime time, long level) {
        this.refNo = refNo;
        this.date = time.toLocalDate();
        this.time = time;
        this.level = level;
    }
}
