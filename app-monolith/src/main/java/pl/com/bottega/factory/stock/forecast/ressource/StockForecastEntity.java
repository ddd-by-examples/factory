package pl.com.bottega.factory.stock.forecast.ressource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.rest.core.config.Projection;
import pl.com.bottega.factory.stock.forecast.StockForecast;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "StockForecast")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class StockForecastEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String refNo;

    public StockForecastEntity(String refNo) {
        this.refNo = refNo;
    }

    public StockForecast getStockForecast() {
        return StaticAccess.calculateQuery(refNo);
    }

    @Projection(types = {StockForecastEntity.class})
    interface CollectionItem {
        String getRefNo();
    }
}
