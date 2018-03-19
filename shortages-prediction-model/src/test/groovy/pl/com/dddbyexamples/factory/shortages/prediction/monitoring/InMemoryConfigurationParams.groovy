package pl.com.dddbyexamples.factory.shortages.prediction.monitoring

import pl.com.dddbyexamples.factory.shortages.prediction.ConfigurationParams

class InMemoryConfigurationParams implements ConfigurationParams {
    int daysAhead;

    @Override
    int shortagePredictionDaysAhead() {
        return daysAhead;
    }
}
