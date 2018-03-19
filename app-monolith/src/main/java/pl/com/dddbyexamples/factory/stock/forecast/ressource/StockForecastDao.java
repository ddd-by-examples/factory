package pl.com.dddbyexamples.factory.stock.forecast.ressource;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.stereotype.Repository;
import pl.com.dddbyexamples.tools.ProjectionRepository;

@Repository
@RepositoryRestResource(path = "stock-forecasts",
        collectionResourceRel = "stock-forecasts",
        itemResourceRel = "stock-forecast",
        excerptProjection = StockForecastDao.CollectionItem.class)
public interface StockForecastDao extends ProjectionRepository<StockForecastEntity, String> {

    @Projection(types = {StockForecastEntity.class})
    interface CollectionItem {
        String getRefNo();
    }

}
