package pl.com.bottega.factory.stock.forecast;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandDao;
import pl.com.bottega.factory.demand.forecasting.projection.CurrentDemandEntity;
import pl.com.bottega.factory.product.management.ProductDescription;
import pl.com.bottega.factory.product.management.ProductDescriptionDao;
import pl.com.bottega.factory.product.management.ProductDescriptionEntity;
import pl.com.bottega.factory.production.planning.projection.ProductionDailyOutputDao;
import pl.com.bottega.factory.production.planning.projection.ProductionDailyOutputEntity;
import pl.com.bottega.factory.shortages.prediction.calculation.CurrentStock;
import pl.com.bottega.factory.stock.forecast.StockForecast.StockForecastBuilder;
import pl.com.bottega.factory.warehouse.WarehouseService;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
class StockForecastQuery {

    private final WarehouseService stocks;
    private final CurrentDemandDao demands;
    private final ProductionDailyOutputDao outputs;
    private final ProductDescriptionDao descriptions;
    private final Clock clock;

    StockForecast get(String refNo) {
        CurrentStock stock = stocks.forRefNo(refNo);
        LocalDate today = LocalDate.now(clock);
        return build(refNo, today, Optional.ofNullable(descriptions.findOne(refNo))
                .map(ProductDescriptionEntity::getDescription).orElse(null), stock,
                this.demands
                        .findByRefNoAndDateGreaterThanEqual(refNo, today).stream()
                        .collect(toMap(
                                CurrentDemandEntity::getDate,
                                CurrentDemandEntity::getLevel
                        )),
                this.outputs
                        .findByRefNoAndDateGreaterThanEqual(refNo, today).stream()
                        .collect(toMap(
                                ProductionDailyOutputEntity::getDate,
                                ProductionDailyOutputEntity::getOutput
                        )));
    }

    private StockForecast build(String refNo, LocalDate today,
                                ProductDescription description, CurrentStock stock,
                                Map<LocalDate, Long> demands,
                                Map<LocalDate, Long> outputs) {
        LocalDate stopAtDay = today.plusDays(15);
        long level = stock.getLevel();
        StockForecastBuilder builder = StockForecast.builder();
        for (LocalDate date = today; date.isBefore(stopAtDay); date = date.plusDays(1)) {
            builder.forecast(
                    new StockForecast.DailyForecast(
                            date,
                            level,
                            level + stock.getLocked(),
                            demands.getOrDefault(date, 0L),
                            outputs.getOrDefault(date, 0L)
                    ));
        }
        return builder
                .refNo(refNo)
                .description(description)
                .build();
    }
}
