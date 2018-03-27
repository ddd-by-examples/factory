package io.dddbyexamples.factory.shortages.prediction.calculation;

import io.dddbyexamples.factory.product.management.RefNoId;

public interface ShortageForecasts {
    ShortageForecast get(RefNoId refNo, int daysAhead);
}
