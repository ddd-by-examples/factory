package pl.com.bottega.factory.demand.forecasting.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.bottega.factory.demand.forecasting.AdjustDemand;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "DemandAdjustment")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DemandAdjustmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String note;
    @Column
    private String customerRepresentative;
    @Column
    @Setter
    private LocalDate cleanAfter;

    @Column(length = 4096)
    @Convert(converter = AdjustDemandAsJson.class)
    private AdjustDemand adjustment;

    public static class AdjustDemandAsJson extends JsonConverter<AdjustDemand> {
        public AdjustDemandAsJson() {
            super(AdjustDemand.class);
        }
    }
}
