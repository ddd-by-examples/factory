package pl.com.bottega.factory.shortages.prediction.calculation

import pl.com.bottega.factory.product.management.RefNoId
import pl.com.bottega.factory.shortages.prediction.Shortages

import java.time.Duration
import java.time.LocalDateTime

trait ShortagesCalculationAssemblerTrait {

    LocalDateTime now = LocalDateTime.now()
    String refNo = "3009000"
    SortedSet<LocalDateTime> times

    Forecasts forecastProvider(Stock stock, Deliveries demands, ProductionOutputs outputs) {
        def forecast = forecast(stock, demands, outputs)
        return { RefNoId refNo, int daysAhead -> forecast } as Forecasts
    }

    Forecast forecast(Stock stock, Deliveries demands, ProductionOutputs outputs) {
        new Forecast(refNo, now, times, stock, outputs, demands)
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

    Deliveries noDeliveries() {
        times = Collections.emptySortedSet()
        new Deliveries([:])
    }

    Deliveries deliveries(Map<LocalDateTime, Long> demands) {
        times = new TreeSet<>(demands.keySet())
        new Deliveries(demands)
    }

    Stock stock(long levels) {
        new Stock(levels, 0)
    }

    Stock stock(long level, long locked) {
        new Stock(level, locked)
    }

    Optional<Shortages> noShortages() {
        Optional.empty()
    }

    Optional<Shortages> shortage(Map<LocalDateTime, Long> missing, long locked = 0) {
        def shortages = Shortages.builder(refNo, locked, now)

        missing.each { time, level -> shortages.missing(time, level) }

        shortages.build()
    }

}
