package pl.com.bottega.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.shortages.prediction.ConfigurationParams;
import pl.com.bottega.factory.shortages.prediction.Shortage;
import pl.com.bottega.factory.shortages.prediction.calculation.ShortageForecast;
import pl.com.bottega.factory.shortages.prediction.calculation.ShortageForecasts;
import pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage.After;

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
        predict(After.DemandChanged);
    }

    void onPlanChanged() {
        predict(After.PlanChanged);
    }

    void onStockChanged() {
        predict(After.StockChanged);
    }

    void onLockedParts() {
        predict(After.LockedParts);
    }

    private void predict(After event) {
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
