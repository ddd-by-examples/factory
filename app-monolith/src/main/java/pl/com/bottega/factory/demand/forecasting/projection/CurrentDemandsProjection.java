package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.DemandEvents;

@AllArgsConstructor
public class CurrentDemandsProjection implements DemandEvents {

    @Override
    public void emit(DemandedLevelsChanged event) {
    }
}
