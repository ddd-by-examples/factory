package pl.com.bottega.factory.shortages.prediction.monitoring

import pl.com.bottega.factory.product.management.RefNoId
import pl.com.bottega.factory.shortages.prediction.ConfigurationParams
import pl.com.bottega.factory.shortages.prediction.Shortage
import pl.com.bottega.factory.shortages.prediction.calculation.ShortageForecasts
import pl.com.bottega.factory.shortages.prediction.calculation.ShortagesCalculationAssembler

import java.time.LocalDateTime

trait ShortagePredictionProcessTrait {

    def refNo = new RefNoId("3009000")
    def now = LocalDateTime.now()
    def forecastAssembler = new ShortagesCalculationAssembler(refNo: refNo, now: now)
    ShortageEvents events

    ShortagePredictionProcess predictionProcess(
            Shortage previouslyFound,
            ShortageForecasts forecastThatWillFindShortages) {

        new ShortagePredictionProcess(
                refNo,
                previouslyFound,
                ShortageDiffPolicy.ValuesAreNotSame,
                forecastThatWillFindShortages,
                defaultConfig(),
                events
        )
    }

    Map<LocalDateTime, Long> someShortages() {
        [(now.plusHours(5)): 500L,
         (now.plusDays(1)) : 500L]
    }

    Map<LocalDateTime, Long> someDifferentShortages() {
        [(now.plusHours(5)): 100L,
         (now.plusDays(1)) : 900L]
    }

    Shortage noShortagesWasPreviouslyFound() {
        null
    }

    Shortage wasPreviouslyFound(Map<LocalDateTime, Long> shortages) {
        forecastAssembler.shortage(shortages).orElse(null)
    }

    ShortageForecasts noShortagesWillBeFound() {
        forecastAssembler.forecastProvider(
                forecastAssembler.stock(1000),
                forecastAssembler.noDeliveries(),
                forecastAssembler.noProductions()
        )
    }

    ShortageForecasts willFindShortages(Map<LocalDateTime, Long> shortages) {
        forecastAssembler.forecastProvider(
                forecastAssembler.stock(0),
                forecastAssembler.deliveries(shortages),
                forecastAssembler.noProductions()
        )
    }

    ConfigurationParams defaultConfig() {
        new InMemoryConfigurationParams(daysAhead: 14)
    }

    NewShortage newShortage(NewShortage.After after, Map<LocalDateTime, Long> missing) {
        new NewShortage(refNo, after, forecastAssembler.shortage(missing).get())
    }

    ShortageSolved shortageSolved() {
        new ShortageSolved(refNo)
    }
}