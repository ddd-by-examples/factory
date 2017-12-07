package pl.com.bottega.factory.delivery.planning.projection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "DeliveryForecast")
@Getter
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

    DeliveryForecastEntity(String refNo, LocalDateTime time, long level) {
        this.refNo = refNo;
        this.date = time.toLocalDate();
        this.time = time;
        this.level = level;
    }
}
