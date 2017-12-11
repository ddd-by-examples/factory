package pl.com.bottega.factory.stock.forecast;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.factory.product.management.ProductDescriptionEntity;
import pl.com.bottega.tools.ProjectionRepository;

@Repository
@RepositoryRestResource(
        path = "stock-forecasts-ex", collectionResourceRel = "stock-forecasts-ex")
public interface StockForecastDao extends ProjectionRepository<ProductDescriptionEntity, Long> {

    @RestResource(path = "refNos", rel = "refNos")
    ProductDescriptionEntity findByRefNo(String refNo);
}
