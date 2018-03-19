package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

import lombok.Value;
import pl.com.dddbyexamples.factory.product.management.RefNoId;
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage;

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
