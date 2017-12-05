package pl.com.bottega.factory.delivery.planning;

import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.DeliveryAutoPlanner;
import pl.com.bottega.factory.product.management.RefNoId;

@Component
public class DeliveryAutoPlannerRepository {
    public DeliveryAutoPlanner get(RefNoId refNo) {
        return null;
    }
}
