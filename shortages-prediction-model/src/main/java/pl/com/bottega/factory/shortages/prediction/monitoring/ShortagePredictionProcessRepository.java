package pl.com.bottega.factory.shortages.prediction.monitoring;

import pl.com.bottega.factory.product.management.RefNoId;

/**
 * Created by michal on 03.02.2017.
 */
public interface ShortagePredictionProcessRepository {
    ShortagePredictionProcess get(RefNoId refNo);

    void save(ShortagePredictionProcess model);
}
