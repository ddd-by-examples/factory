package io.dddbyexamples.factory.shortages.prediction.monitoring;

import io.dddbyexamples.factory.product.management.RefNoId;

interface ShortagePredictionProcessRepository {
    ShortagePredictionProcess get(RefNoId refNo);

    void save(ShortagePredictionProcess model);
}
