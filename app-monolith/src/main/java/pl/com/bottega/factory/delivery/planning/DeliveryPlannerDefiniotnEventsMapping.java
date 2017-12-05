package pl.com.bottega.factory.delivery.planning;

import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.demand.forecasting.projection.DeliveryForecastProjection;

@Component
@RepositoryEventHandler
public class DeliveryPlannerDefiniotnEventsMapping {

    DeliveryForecastProjection projection;
    @HandleAfterSave
    public void handle(DeliveryPlannerDefinitionEntity entity) {
        projection.handleDeliveryPlannerDefinitionChange(entity.getRefNo());
    }
}
