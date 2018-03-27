package io.dddbyexamples.factory.delivery.planning.definition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import io.dddbyexamples.tools.JsonConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "DeliveryPlannerDefinition")
@Table(schema = "delivery_planning")
@Getter
@NoArgsConstructor
public class DeliveryPlannerDefinitionEntity implements Serializable {

    @Id
    private String refNo;
    @Convert(converter = DefinitionAsJson.class)
    private DeliveryPlannerDefinition definition;

    public DeliveryPlannerDefinitionEntity(String refNo, DeliveryPlannerDefinition definition) {
        this.refNo = refNo;
        this.definition = definition;
    }

    public static class DefinitionAsJson extends JsonConverter<DeliveryPlannerDefinition> {
        public DefinitionAsJson() {
            super(DeliveryPlannerDefinition.class);
        }
    }
}
