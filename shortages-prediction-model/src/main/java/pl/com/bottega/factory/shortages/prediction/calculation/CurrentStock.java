package pl.com.bottega.factory.shortages.prediction.calculation;

import lombok.Value;

@Value
public class CurrentStock {
    long level;
    long locked;
}
