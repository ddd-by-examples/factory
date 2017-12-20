package pl.com.bottega.factory.demand.forecasting.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.bottega.factory.demand.forecasting.AdjustDemand;
import pl.com.bottega.tools.JsonConverter;

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

    public static class AdjustDemandAsJson extends JsonConverter<AdjustDemand> {
        public AdjustDemandAsJson() {
            super(AdjustDemand.class);
        }
    }
}
