package io.dddbyexamples.factory.shortages.prediction.monitoring;

import io.dddbyexamples.factory.shortages.prediction.Shortage;

interface ShortageDiffPolicy {

    ShortageDiffPolicy ValuesAreNotSame = Shortage::areNotSame;

    boolean areDifferent(Shortage previous, Shortage found);
}
