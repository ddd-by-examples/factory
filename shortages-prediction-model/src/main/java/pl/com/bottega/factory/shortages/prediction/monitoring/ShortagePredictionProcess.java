package pl.com.bottega.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.shortages.prediction.Configuration;
import pl.com.bottega.factory.shortages.prediction.Shortages;
import pl.com.bottega.factory.shortages.prediction.calculation.Forecast;
import pl.com.bottega.factory.shortages.prediction.calculation.Forecasts;
import pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage.After;

import java.util.Optional;

/**
 * Created by michal on 02.02.2017.
 */
@AllArgsConstructor
public class ShortagePredictionProcess {

    private final RefNoId refNo;
    private Shortages known;

    private final ShortageDiffPolicy diffPolicy;
    private final Forecasts forecasts;
    private final Configuration configuration;
    private final ShortageEvents events;

    public void onDemandChanged() {
        predict(After.DemandChanged);
    }

    public void onPlanChanged() {
        predict(After.PlanChanged);
    }

    public void onStockChanged() {
        predict(After.StockChanged);
    }

    public void onLockedParts() {
        predict(After.LockedParts);
    }

    private void predict(After event) {
        Forecast forecast = forecasts.get(refNo,
                configuration.shortagePredictionDaysAhead());

        Optional<Shortages> newlyFound = forecast.findShortages();

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
