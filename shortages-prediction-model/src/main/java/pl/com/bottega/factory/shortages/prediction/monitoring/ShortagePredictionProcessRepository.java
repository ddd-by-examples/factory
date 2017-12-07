package pl.com.bottega.factory.shortages.prediction.monitoring;

/**
 * Created by michal on 03.02.2017.
 */
public interface ShortagePredictionProcessRepository {
    ShortagePredictionProcess get(String refNo);

    void save(ShortagePredictionProcess model);
}
