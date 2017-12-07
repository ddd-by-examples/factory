package pl.com.bottega.factory.delivery.planning.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;

@Entity(name = "DeliveryPlannerDefinition")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "refNo")
public class DeliveryPlannerDefinitionEntity {

    @Id
    @Column
    private String refNo;

    @Column
    @Convert(converter = DescriptionAsJson.class)
    DeliveryPlannerDefinition definition;

    public DeliveryPlannerDefinitionEntity(String refNo, DeliveryPlannerDefinition definition) {
        this.refNo = refNo;
        this.definition = definition;
    }

    public static class DescriptionAsJson extends JsonConverter<DeliveryPlannerDefinition> {
        public DescriptionAsJson() {
            super(DeliveryPlannerDefinition.class);
        }
    }
}
