package io.dddbyexamples.factory.stock.forecast.ressource;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import io.dddbyexamples.factory.product.management.RefNoId;
import io.dddbyexamples.factory.stock.forecast.StockForecastQuery;

@Component
@AllArgsConstructor
class StockForecastResourceProcessor implements ResourceProcessor<Resource<StockForecastEntity>> {

    private final StockForecastQuery query;

    @Override
    public Resource<StockForecastEntity> process(Resource<StockForecastEntity> resource) {
        resource.getContent().setStockForecast(
                query.get(new RefNoId(resource.getContent().getRefNo()))
        );
        return resource;
    }
}
