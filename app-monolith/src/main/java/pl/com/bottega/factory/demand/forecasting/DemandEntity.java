package pl.com.bottega.factory.demand.forecasting;

import lombok.*;
import pl.com.bottega.factory.delivery.planning.definition.DeliveryPlannerDefinition;
import pl.com.bottega.tools.JsonConverter;
import pl.com.bottega.tools.TechnicalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity(name = "Demand")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DemandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ProductDemandEntity product;
    @Column
    private LocalDate date;

    @Column
    @Convert(converter = DemandAsJson.class)
    private DemandValue value;

    DemandEntity(ProductDemandEntity product, LocalDate date) {
        this.product = product;
        this.date = date;
    }

    DemandEntityId createId() {
        return new DemandEntityId(product.getRefNo(), date, id);
    }

    void set(Demand demand, Adjustment adjustment) {
        value = new DemandValue(demand, adjustment);
    }

    DemandValue get() {
        return Optional.ofNullable(value)
                .orElse(DemandValue.NO_VAL);
    }

    @Value
    static class DemandValue {
        public static final DemandValue NO_VAL = new DemandValue(null, null);

        Demand documented;
        Adjustment adjustment;
    }

    public static class DemandAsJson extends JsonConverter<DemandValue> {
        public DemandAsJson() {
            super(DemandValue.class);
        }
    }

    @Getter
    static class DemandEntityId extends DailyId implements TechnicalId {

        Long id;

        DemandEntityId(String refNo, LocalDate date) {
            super(refNo, date);
        }

        DemandEntityId(String refNo, LocalDate date, Long id) {
            super(refNo, date);
            this.id = id;
        }
    }
}
