package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import pl.com.dddbyexamples.factory.product.management.RefNoId;
import pl.com.dddbyexamples.factory.shortages.prediction.ConfigurationParams;
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage;
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.ShortageForecast;
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.ShortageForecasts;

import java.util.Optional;

/**
 * Created by michal on 02.02.2017.
 */
@AllArgsConstructor
class ShortagePredictionProcess {

    private final RefNoId refNo;
    private Shortage known;

    private final ShortageDiffPolicy diffPolicy;
    private final ShortageForecasts forecasts;
    private final ConfigurationParams configuration;
    private final ShortageEvents events;

    void onDemandChanged() {
        predict(NewShortage.After.DemandChanged);
    }

    void onPlanChanged() {
        predict(NewShortage.After.PlanChanged);
    }

    void onStockChanged() {
        predict(NewShortage.After.StockChanged);
    }

    void onLockedParts() {
        predict(NewShortage.After.LockedParts);
    }

    private void predict(NewShortage.After event) {
        ShortageForecast forecast = forecasts.get(refNo,
                configuration.shortagePredictionDaysAhead());

        Optional<Shortage> newlyFound = forecast.findShortages();

        boolean areDifferent = diffPolicy.areDifferent(this.known, newlyFound.orElse(null));
        if (areDifferent && newlyFound.isPresent()) {
            this.known = newlyFound.get();
            events.emit(new NewShortage(refNo, event, known));
        } else if (known != null && !newlyFound.isPresent()) {
            this.known = null;
            events.emit(new ShortageSolved(refNo));
        }
    }
}
