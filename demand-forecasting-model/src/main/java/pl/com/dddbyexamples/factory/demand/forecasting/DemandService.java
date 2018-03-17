package pl.com.dddbyexamples.factory.demand.forecasting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DemandService {

    private final ProductDemandRepository repository;

    public void initNewProduct(String refNo) {
        repository.initNewProduct(refNo);
    }

    public void process(Document document) {
        ProductDemand model = repository.get(document.getRefNo());
        model.process(document);
        repository.save(model);
    }

    public void adjust(AdjustDemand adjustDemand) {
        ProductDemand model = repository.get(adjustDemand.getRefNo());
        model.adjust(adjustDemand);
        repository.save(model);
    }

    public void review(ApplyReviewDecision reviewDecision) {
        ProductDemand model = repository.get(reviewDecision.getRefNo());
        model.review(reviewDecision);
        repository.save(model);
    }
}
