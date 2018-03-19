package pl.com.dddbyexamples.factory.demand.forecasting.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.dddbyexamples.factory.demand.forecasting.AdjustDemand;
import pl.com.dddbyexamples.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "DemandAdjustment")
@Table(schema = "demand_forecasting")
@Getter
@NoArgsConstructor
public class DemandAdjustmentEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String note;
    private String customerRepresentative;
    @Convert(converter = AdjustDemandAsJson.class)
    private AdjustDemand adjustment;

    @Setter
    private LocalDate cleanAfter;

    DemandAdjustmentEntity(String note, String customerRepresentative, AdjustDemand adjustment) {
        this.note = note;
        this.customerRepresentative = customerRepresentative;
        this.adjustment = adjustment;
    }

    public static class AdjustDemandAsJson extends JsonConverter<AdjustDemand> {
        public AdjustDemandAsJson() {
            super(AdjustDemand.class);
        }
    }
}
