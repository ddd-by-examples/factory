package pl.com.dddbyexamples.factory.shortages.prediction.calculation

import pl.com.dddbyexamples.factory.product.management.RefNoId
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.ShortageForecast
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.ShortageForecasts
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.Stock

import java.time.Duration
import java.time.LocalDateTime

trait ShortagesCalculationTrait {

    LocalDateTime now = LocalDateTime.now()
    String refNo = "3009000"
    SortedSet<LocalDateTime> times

    ShortageForecasts forecastProvider(Stock stock, DeliveriesForecast demands, ProductionOutputs outputs) {
        def forecast = forecast(stock, demands, outputs)
        return { RefNoId refNo, int daysAhead -> forecast } as ShortageForecasts
    }

    ShortageForecast forecast(Stock stock, DeliveriesForecast demands, ProductionOutputs outputs) {
        new ShortageForecast(refNo, now, times, stock, outputs, demands)
    }

    ProductionOutputs noProductions() {
        new ProductionForecast([])
                .outputsInTimes(now, times)
    }

    ProductionOutputs plan(List<ProductionForecast.Item> productions) {
        new ProductionForecast(productions)
                .outputsInTimes(now, times)
    }

    ProductionForecast.Item production(LocalDateTime start, Duration duration, int partsPerMinute) {
        new ProductionForecast.Item(start, duration, partsPerMinute)
    }

    DeliveriesForecast noDeliveries() {
        times = Collections.emptySortedSet()
        new DeliveriesForecast([:])
    }

    DeliveriesForecast deliveries(Map<LocalDateTime, Long> demands) {
        times = new TreeSet<>(demands.keySet())
        new DeliveriesForecast(demands)
    }

    Stock stock(long levels) {
        new Stock(levels, 0)
    }

    Stock stock(long level, long locked) {
        new Stock(level, locked)
    }

    Optional<Shortage> noShortages() {
        Optional.empty()
    }

    Optional<Shortage> shortage(Map<LocalDateTime, Long> missing, long locked = 0) {
        def shortages = Shortage.builder(refNo, locked, now)

        missing.each { time, level -> shortages.missing(time, level) }

        shortages.build()
    }

}
