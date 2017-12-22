package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DemandService {

    private final DemandORMRepository repository;

    public void init(String refNo) {
        repository.initDemandsFor(refNo);
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

    public void review(ToReview review, ReviewDecision decision) {
        ProductDemand model = repository.get(review.getRefNo());
        model.review(review, decision);
        repository.save(model);
    }
}
