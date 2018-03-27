package io.dddbyexamples.factory.shortages.prediction.monitoring

import io.dddbyexamples.factory.shortages.prediction.ConfigurationParams

class InMemoryConfigurationParams implements ConfigurationParams {
    int daysAhead;

    @Override
    int shortagePredictionDaysAhead() {
        return daysAhead;
    }
}
