package pl.com.bottega.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastDao;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastEntity;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.production.planning.projection.ProductionOutputDao;
import pl.com.bottega.factory.shortages.prediction.calculation.ProductionForecast.Item;
import pl.com.bottega.factory.warehouse.WarehouseService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Component
@AllArgsConstructor
class ForecastORMProvider implements Forecasts {

    private final WarehouseService stocks;
    private final DeliveryForecastDao deliveries;
    private final ProductionOutputDao outputs;
    private final Clock clock;

    @Override
    public Forecast get(RefNoId refNo, int daysAhead) {
        Stock stock = stocks.forRefNo(refNo);
        Instant now = Instant.now(clock);
        LocalDateTime time = now.atZone(clock.getZone()).toLocalDateTime();

        Map<LocalDateTime, Long> deliveries = this.deliveries
                .findByRefNoAndTimeGreaterThanEqual(refNo.getRefNo(), time).stream()
                .collect(toMap(
                        DeliveryForecastEntity::getTime,
                        DeliveryForecastEntity::getLevel
                ));
        SortedSet<LocalDateTime> deliveryTimes = new TreeSet<>(deliveries.keySet());

        Deliveries demand = new Deliveries(deliveries);

        ProductionOutputs outputs = new ProductionForecast(
                this.outputs.findByRefNoAndStartGreaterThanEqual(refNo.getRefNo(), time).stream()
                        .map(e -> new Item(
                                e.getStart(),
                                e.getDuration(),
                                e.getPartsPerMinute()))
                        .collect(Collectors.toList())
        ).outputsInTimes(time, deliveryTimes);

        return new Forecast(refNo.getRefNo(), time, deliveryTimes, stock, outputs, demand);
    }
}
