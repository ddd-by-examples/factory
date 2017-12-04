package pl.com.bottega.factory.shortages.prediction.monitoring;

import pl.com.bottega.factory.shortages.prediction.Shortages;

interface ShortageDiffPolicy {

    ShortageDiffPolicy ValuesAreEquals = Shortages::areNotSame;

    boolean areDifferent(Shortages previous, Shortages found);
}
