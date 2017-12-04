package pl.com.bottega.factory.shortages.prediction.monitoring

import pl.com.bottega.factory.shortages.prediction.Configuration

class InMemoryConfiguration implements Configuration {
    int daysAhead;

    @Override
    int shortagePredictionDaysAhead() {
        return daysAhead;
    }
}
