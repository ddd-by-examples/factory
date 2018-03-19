package pl.com.dddbyexamples.factory.demand.forecasting;

import lombok.Value;

@Value
public class DemandValue {
    Demand documented;
    Adjustment adjustment;
}
