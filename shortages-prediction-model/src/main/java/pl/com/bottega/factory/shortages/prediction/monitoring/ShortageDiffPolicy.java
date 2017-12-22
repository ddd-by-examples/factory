package pl.com.bottega.factory.shortages.prediction.monitoring;

import pl.com.bottega.factory.shortages.prediction.Shortage;

interface ShortageDiffPolicy {

    ShortageDiffPolicy ValuesAreNotSame = Shortage::areNotSame;

    boolean areDifferent(Shortage previous, Shortage found);
}
