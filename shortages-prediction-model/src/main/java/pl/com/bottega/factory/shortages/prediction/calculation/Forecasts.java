package pl.com.bottega.factory.shortages.prediction.calculation;

import pl.com.bottega.factory.product.management.RefNoId;

public interface Forecasts {
    Forecast get(RefNoId refNo, int daysAhead);
}
