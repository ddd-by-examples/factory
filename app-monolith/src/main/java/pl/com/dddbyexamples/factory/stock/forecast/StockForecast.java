package pl.com.dddbyexamples.factory.stock.forecast;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class StockForecast {

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
