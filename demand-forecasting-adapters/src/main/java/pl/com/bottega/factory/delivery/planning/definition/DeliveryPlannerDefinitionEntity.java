package pl.com.bottega.factory.delivery.planning.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "DeliveryPlannerDefinition")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "refNo")
public class DeliveryPlannerDefinitionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String refNo;
    @Column
    @Convert(converter = DescriptionAsJson.class)
    private DeliveryPlannerDefinition definition;

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
