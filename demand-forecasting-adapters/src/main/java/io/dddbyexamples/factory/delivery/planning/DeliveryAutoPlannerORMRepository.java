package io.dddbyexamples.factory.delivery.planning;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import io.dddbyexamples.factory.delivery.planning.definition.DeliveryPlannerDefinitionDao;
import io.dddbyexamples.factory.delivery.planning.definition.DeliveryPlannerDefinitionEntity;

import java.util.Collections;

@Component
@AllArgsConstructor
public class DeliveryAutoPlannerORMRepository {

    DeliveryPlannerDefinitionDao dao;

    public DeliveryAutoPlanner get(String refNo) {
        return new DeliveryAutoPlanner(refNo,
                dao.findById(refNo)
                        .map(DeliveryPlannerDefinitionEntity::getDefinition)
                        .map(x -> x.map(DeliveriesSuggestion::timesAndFractions))
                        .orElse(Collections.emptyMap()));
    }
}
