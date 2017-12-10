package pl.com.bottega.factory.delivery.planning;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.definition.DeliveryPlannerDefinitionDao;
import pl.com.bottega.factory.delivery.planning.definition.DeliveryPlannerDefinitionEntity;

import java.util.Collections;

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
public class DeliveryAutoPlannerORMRepository {

    DeliveryPlannerDefinitionDao dao;

    public DeliveryAutoPlanner get(String refNo) {
        return new DeliveryAutoPlanner(refNo,
                ofNullable(dao.findByRefNo(refNo))
                        .map(DeliveryPlannerDefinitionEntity::getDefinition)
                        .map(x -> x.map(DeliveriesSuggestion::timesAndFractions))
                        .orElse(Collections.emptyMap()));
    }
}
