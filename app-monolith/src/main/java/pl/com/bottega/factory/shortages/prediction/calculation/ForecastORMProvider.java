package pl.com.bottega.factory.shortages.prediction.calculation;

import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastDao;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastEntity;
import lombok.AllArgsConstructor;
import pl.com.bottega.factory.production.planning.projection.ProductionOutputDao;
import pl.com.bottega.factory.shortages.prediction.calculation.ProductionForecast.Item;
import pl.com.bottega.factory.warehouse.WarehouseService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Component
@AllArgsConstructor
class ForecastORMProvider implements Forecasts {

    private WarehouseService stocks;
    private DeliveryForecastDao demands;
    private ProductionOutputDao outputs;
    private Clock clock;

    @Override
    public Forecast get(String refNo, int daysAhead) {
        CurrentStock stock = stocks.forRefNo(refNo);
        Instant now = Instant.now(clock);
        LocalDateTime time = now.atZone(clock.getZone()).toLocalDateTime();

        Map<LocalDateTime, Long> demands = this.demands
                .findByRefNoAndDateGreaterThanEqual(refNo, now).stream()
                .collect(toMap(
                        DeliveryForecastEntity::getTime,
                        DeliveryForecastEntity::getLevel
                ));
        List<LocalDateTime> times = new ArrayList<>(demands.keySet());

        Demands demand = new Demands(demands);

        ProductionOutputs outputs = new ProductionForecast(
                this.outputs.findByRefNoAndStartGreaterThanEqual(refNo, now).stream()
                        .map(e -> new Item(
                                e.getStart(),
                                e.getDuration(),
                                e.getPartsPerMinute()))
                        .collect(Collectors.toList())
        ).outputsInTimes(time, demands.keySet());

        return new Forecast(refNo, time, times, stock, outputs, demand);
    }
}
