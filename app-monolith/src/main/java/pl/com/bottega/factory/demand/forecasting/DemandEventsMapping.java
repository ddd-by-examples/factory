package pl.com.bottega.factory.demand.forecasting;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
class DemandEventsMapping implements DemandEvents {

    @Override
    public void emit(DemandedLevelsChanged event) {
    }
}
