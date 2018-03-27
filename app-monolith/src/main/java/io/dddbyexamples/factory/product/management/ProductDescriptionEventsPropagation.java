package io.dddbyexamples.factory.product.management;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import io.dddbyexamples.factory.demand.forecasting.DemandService;
import io.dddbyexamples.factory.stock.forecast.ressource.StockForecastDao;
import io.dddbyexamples.factory.stock.forecast.ressource.StockForecastEntity;

@Component
@Transactional
@AllArgsConstructor
@RepositoryEventHandler
public class ProductDescriptionEventsPropagation {

    private final DemandService demandService;
    private final StockForecastDao stockForecasts;

    @HandleAfterCreate
    public void handleCreate(ProductDescriptionEntity entity) {
        demandService.initNewProduct(entity.getRefNo());
        stockForecasts.save(new StockForecastEntity(entity.getRefNo()));
    }

    @HandleAfterDelete
    public void handleDelete(ProductDescriptionEntity entity) {
        stockForecasts.deleteById(entity.getRefNo());
    }
}
