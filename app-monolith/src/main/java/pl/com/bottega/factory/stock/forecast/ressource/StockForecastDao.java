package pl.com.bottega.factory.stock.forecast.ressource;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionRepository;

@Repository
@RepositoryRestResource(path = "stock-forecasts",
        collectionResourceRel = "stock-forecasts",
        itemResourceRel = "stock-forecast",
        excerptProjection = StockForecastEntity.CollectionItem.class)
public interface StockForecastDao extends ProjectionRepository<StockForecastEntity, String> {

}
