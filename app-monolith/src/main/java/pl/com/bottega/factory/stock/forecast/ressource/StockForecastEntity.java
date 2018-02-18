package pl.com.bottega.factory.stock.forecast.ressource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.bottega.factory.stock.forecast.StockForecast;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "StockForecast")
@Table(schema = "shortages_prediction")
@Getter
@NoArgsConstructor
public class StockForecastEntity implements Serializable {

    @Id
    private String refNo;
    @Setter
    private transient StockForecast stockForecast;

    public StockForecastEntity(String refNo) {
        this.refNo = refNo;
    }
}
