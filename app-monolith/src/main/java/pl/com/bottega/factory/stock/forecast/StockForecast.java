package pl.com.bottega.factory.stock.forecast;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.com.bottega.factory.product.management.ProductDescription;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
class StockForecast {

    String refNo;
    ProductDescription description;
    @Singular
    List<DailyForecast> forecasts;

    @Value
    static class DailyForecast {
        LocalDate date;
        long stock;
        long withLocked;
        long demand;
        long output;
    }
}
