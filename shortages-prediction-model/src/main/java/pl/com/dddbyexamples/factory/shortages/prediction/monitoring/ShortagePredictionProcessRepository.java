package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

import pl.com.dddbyexamples.factory.product.management.RefNoId;

interface ShortagePredictionProcessRepository {
    ShortagePredictionProcess get(RefNoId refNo);

    void save(ShortagePredictionProcess model);
}
