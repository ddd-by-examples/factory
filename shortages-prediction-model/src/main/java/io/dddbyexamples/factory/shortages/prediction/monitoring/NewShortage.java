package io.dddbyexamples.factory.shortages.prediction.monitoring;

import lombok.Value;
import io.dddbyexamples.factory.product.management.RefNoId;
import io.dddbyexamples.factory.shortages.prediction.Shortage;

/**
 * Created by michal on 03.02.2017.
 */
@Value
public class NewShortage {

    public enum After {DemandChanged, PlanChanged, StockChanged, LockedParts}

    RefNoId refNo;
    After trigger;
    Shortage shortage;
}
