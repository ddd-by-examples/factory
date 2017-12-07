package pl.com.bottega.factory.delivery.planning.definition;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.projection.DeliveryForecastProjection;

@Component
@AllArgsConstructor
@RepositoryEventHandler
public class DeliveryPlannerDefinitionEventsMapping {

    private DeliveryForecastProjection projection;

    @HandleAfterSave
    public void handle(DeliveryPlannerDefinitionEntity entity) {
        projection.handleDeliveryPlannerDefinitionChange(entity.getRefNo());
    }
}
