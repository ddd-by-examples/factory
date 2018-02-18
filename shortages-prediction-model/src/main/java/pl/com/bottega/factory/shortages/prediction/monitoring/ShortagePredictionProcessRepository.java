package pl.com.bottega.factory.shortages.prediction.monitoring;

import pl.com.bottega.factory.product.management.RefNoId;

interface ShortagePredictionProcessRepository {
    ShortagePredictionProcess get(RefNoId refNo);

    void save(ShortagePredictionProcess model);
}
