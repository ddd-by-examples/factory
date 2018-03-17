package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import pl.com.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged;

@AllArgsConstructor
public class ShortagePredictionService {

    private final ShortagePredictionProcessRepository repository;

    public void predictShortages(DemandedLevelsChanged event) {
        ShortagePredictionProcess model = repository.get(event.getRefNo());
        model.onDemandChanged();
        repository.save(model);
    }

    //public void predictShortages(ProductionChanged event) { service.onPlanChanged(event.getId().getRefNo()); }

    //public void predictShortages(StockChanged event) { service.onStockChanged(event.getId().getRefNo()); }
}
