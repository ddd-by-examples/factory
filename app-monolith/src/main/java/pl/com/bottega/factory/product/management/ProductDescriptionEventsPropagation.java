package pl.com.bottega.factory.product.management;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.demand.forecasting.DemandService;
import pl.com.bottega.factory.stock.forecast.ressource.StockForecastDao;
import pl.com.bottega.factory.stock.forecast.ressource.StockForecastEntity;

@Component
@AllArgsConstructor
@RepositoryEventHandler
public class ProductDescriptionEventsPropagation {

    private final DemandService demandService;
    private final StockForecastDao stockForecasts;

    @HandleAfterCreate
    public void handleCreate(ProductDescriptionEntity entity) {
        demandService.init(entity.getRefNo());
        stockForecasts.save(new StockForecastEntity(entity.getRefNo()));
    }

    @HandleAfterDelete
    public void handleDelete(ProductDescriptionEntity entity) {
        stockForecasts.delete(entity.getRefNo());
    }
}
