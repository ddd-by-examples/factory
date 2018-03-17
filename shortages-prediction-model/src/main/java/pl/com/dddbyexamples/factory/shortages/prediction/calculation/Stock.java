package pl.com.dddbyexamples.factory.shortages.prediction.calculation;

import lombok.Value;

@Value
public class Stock {
    long level;
    long locked;
}
