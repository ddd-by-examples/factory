package io.dddbyexamples.factory.demand.forecasting.persistence;

import io.dddbyexamples.factory.demand.forecasting.DailyId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.dddbyexamples.factory.demand.forecasting.DemandValue;
import io.dddbyexamples.tools.JsonConverter;
import io.dddbyexamples.tools.TechnicalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "Demand")
@Table(schema = "demand_forecasting")
@Getter
@NoArgsConstructor
public class DemandEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    private LocalDate date;
    @Setter
    @Convert(converter = DemandAsJson.class)
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
