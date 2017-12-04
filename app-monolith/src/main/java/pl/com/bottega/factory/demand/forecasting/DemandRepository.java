package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.demand.forecasting.persistence.DemandDao;
import pl.com.bottega.factory.demand.forecasting.persistence.ProductDemandDao;

import java.time.Clock;

@Component
@AllArgsConstructor
class DemandRepository {

    private Clock clock;
    private DemandEventsMapping events;
    private ProductDemandDao rootDao;
    private DemandDao demandDao;

    ProductDemand get(String refNo) {
        return null;
    }

    void save(ProductDemand model) {
    }
}
