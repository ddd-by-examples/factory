package pl.com.bottega.factory.shortages.prediction.calculation

import pl.com.bottega.factory.shortages.prediction.Shortages

import java.time.Duration
import java.time.LocalDateTime

trait ShortagesCalculationAssemblerTrait {

    LocalDateTime now = LocalDateTime.now()
    String refNo = "3009000"
    Set<LocalDateTime> times

    Forecasts forecastProvider(CurrentStock stock, Demands demands, ProductionOutputs outputs) {
        def forecast = forecast(stock, demands, outputs)
        return { String refNo, int daysAhead -> forecast } as Forecasts
    }

    Forecast forecast(CurrentStock stock, Demands demands, ProductionOutputs outputs) {
        new Forecast(refNo, now, times as List, stock, outputs, demands)
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

    Demands noDemands() {
        times = Collections.emptySet()
        new Demands([:])
    }

    Demands demands(Map<LocalDateTime, Long> demands) {
        times = demands.keySet()
        new Demands(demands)
    }

    CurrentStock stock(long levels) {
        new CurrentStock(levels, 0)
    }

    CurrentStock stock(long level, long locked) {
        new CurrentStock(level, locked)
    }

    Optional<Shortages> noShortages() {
        Optional.empty()
    }

    Optional<Shortages> shortage(Map<LocalDateTime, Long> missing, long locked = 0) {
        def shortages = Shortages.builder(refNo, locked, now)

        missing.each { time, level -> shortages.add(time, level) }

        shortages.build()
    }

}
