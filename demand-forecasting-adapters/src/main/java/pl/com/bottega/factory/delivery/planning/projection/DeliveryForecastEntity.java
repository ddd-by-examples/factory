package pl.com.bottega.factory.delivery.planning.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "DeliveryForecast")
@Table(schema = "delivery_planning")
@Getter
@NoArgsConstructor
public class DeliveryForecastEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    private LocalDate date;
    private LocalDateTime time;
    private long level;

    DeliveryForecastEntity(String refNo, LocalDateTime time, long level) {
        this.refNo = refNo;
        this.date = time.toLocalDate();
        this.time = time;
        this.level = level;
    }
}
