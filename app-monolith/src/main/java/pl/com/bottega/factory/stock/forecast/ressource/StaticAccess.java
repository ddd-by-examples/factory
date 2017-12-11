package pl.com.bottega.factory.stock.forecast.ressource;

import org.springframework.stereotype.Component;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.stock.forecast.StockForecast;
import pl.com.bottega.factory.stock.forecast.StockForecastQuery;

@Component
class StaticAccess {

    private static StockForecastQuery query;

    StaticAccess(StockForecastQuery query) {
        StaticAccess.query = query;
    }

    static StockForecast calculateQuery(String refNo) {
        return query.get(new RefNoId(refNo));
    }
}
