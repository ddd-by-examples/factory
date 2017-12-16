package pl.com.bottega.factory.demand.forecasting.persistence;

import lombok.*;
import pl.com.bottega.factory.demand.forecasting.DailyId;
import pl.com.bottega.factory.demand.forecasting.DemandValue;
import pl.com.bottega.tools.JsonConverter;
import pl.com.bottega.tools.TechnicalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "Demand")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class DemandEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    String refNo;
    @Column
    private LocalDate date;
    @Column
    @Convert(converter = DemandAsJson.class)
    @Setter
    private DemandValue value;

    public DemandEntity(String refNo, LocalDate date) {
        this.refNo = refNo;
        this.date = date;
        this.value = new DemandValue(null, null);
    }

    public DemandEntityId createId() {
        return new DemandEntityId(refNo, date, id);
    }

    public static class DemandAsJson extends JsonConverter<DemandValue> {
        public DemandAsJson() {
            super(DemandValue.class);
        }
    }

    @Getter
    public static class DemandEntityId extends DailyId implements TechnicalId {

        private Long id;

        public DemandEntityId(String refNo, LocalDate date) {
            super(refNo, date);
        }

        DemandEntityId(String refNo, LocalDate date, Long id) {
            super(refNo, date);
            this.id = id;
        }
    }
}
