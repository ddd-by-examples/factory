package pl.com.bottega.factory.shortages.prediction.calculation;

public interface Forecasts {
    Forecast get(String refNo, int daysAhead);
}
