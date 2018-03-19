package pl.com.dddbyexamples.factory.delivery.planning.definition;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.dddbyexamples.factory.delivery.planning.projection.DeliveryForecastProjection;

@Component
@Transactional
@AllArgsConstructor
@RepositoryEventHandler
public class DeliveryPlannerDefinitionEventsPropagation {

    private DeliveryForecastProjection projection;

    @HandleAfterSave
    @HandleAfterCreate
    public void handleCreateAndUpdate(DeliveryPlannerDefinitionEntity entity) {
        projection.applyDeliveryPlannerDefinitionChange(entity.getRefNo());
    }
}
