package pl.com.bottega.factory.stock.forecast.ressource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.rest.core.config.Projection;
import pl.com.bottega.factory.product.management.ProductDescriptionEntity;
import pl.com.bottega.factory.stock.forecast.StockForecast;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "StockForecast")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "product")
public class StockForecastEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ProductDescriptionEntity product;

    public StockForecast getStockForecast() {
        return StaticAccess.calculateQuery(product.getRefNo());
    }

    @Projection(types = {StockForecastEntity.class})
    interface CollectionItem {
        ProductDescriptionEntity getProduct();
    }
}
