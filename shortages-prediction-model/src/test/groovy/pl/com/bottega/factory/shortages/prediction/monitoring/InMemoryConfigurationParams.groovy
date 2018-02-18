package pl.com.bottega.factory.shortages.prediction.monitoring

import pl.com.bottega.factory.shortages.prediction.ConfigurationParams

class InMemoryConfigurationParams implements ConfigurationParams {
    int daysAhead;

    @Override
    int shortagePredictionDaysAhead() {
        return daysAhead;
    }
}
