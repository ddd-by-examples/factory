package pl.com.bottega.factory.shortages.prediction;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged;
import pl.com.bottega.factory.shortages.prediction.monitoring.ShortagePredictionProcess;
import pl.com.bottega.factory.shortages.prediction.monitoring.ShortagePredictionProcessRepository;

@Lazy
@Component
@AllArgsConstructor
public class ShortagePredictionEventsMapping {

    private final ShortagePredictionProcessRepository repository;

    public void predictShortages(DemandedLevelsChanged event) {
        ShortagePredictionProcess model = repository.get(event.getRefNo());
        model.onDemandChanged();
        repository.save(model);
    }

    //public void predictShortages(ProductionChanged event) { service.onPlanChanged(event.getId().getRefNo()); }

    //public void predictShortages(StockChanged event) { service.onStockChanged(event.getId().getRefNo()); }
}
