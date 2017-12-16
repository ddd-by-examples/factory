package pl.com.bottega.factory.shortages.prediction.monitoring;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.bottega.factory.demand.forecasting.DemandedLevelsChanged;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ShortagePredictionService {

    private final ShortagePredictionProcessORMRepository repository;

    public void predictShortages(DemandedLevelsChanged event) {
        ShortagePredictionProcess model = repository.get(event.getRefNo());
        model.onDemandChanged();
        repository.save(model);
    }

    //public void predictShortages(ProductionChanged event) { service.onPlanChanged(event.getId().getRefNo()); }

    //public void predictShortages(StockChanged event) { service.onStockChanged(event.getId().getRefNo()); }
}
