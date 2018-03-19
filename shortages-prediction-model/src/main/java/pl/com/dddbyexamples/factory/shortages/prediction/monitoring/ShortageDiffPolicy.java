package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

import pl.com.dddbyexamples.factory.shortages.prediction.Shortage;

interface ShortageDiffPolicy {

    ShortageDiffPolicy ValuesAreNotSame = Shortage::areNotSame;

    boolean areDifferent(Shortage previous, Shortage found);
}
