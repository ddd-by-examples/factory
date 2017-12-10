package pl.com.bottega.factory.stock.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.com.bottega.factory.product.management.ProductDescription;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Value
@Builder
class StockForecast {

    String refNo;
    ProductDescription description;
    @Singular
    List<DailyForecast> forecasts;

    @Value
    static class DailyForecast {
        @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
        LocalDate date;
        long stock;
        long withLocked;
        long demand;
        long output;
    }
}
