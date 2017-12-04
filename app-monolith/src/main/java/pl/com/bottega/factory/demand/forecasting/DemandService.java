package pl.com.bottega.factory.demand.forecasting;

import javax.transaction.Transactional;

public class DemandService {

    private DemandRepository repository;

    public void process(Document document) {
        ProductDemand model = repository.get(document.getRefNo());
        model.process(document);
    }

    @Transactional
    public void adjust(AdjustDemand adjustDemand) {
        ProductDemand model = repository.get(adjustDemand.getRefNo());
        model.adjust(adjustDemand);
        repository.save(model);
    }
}
