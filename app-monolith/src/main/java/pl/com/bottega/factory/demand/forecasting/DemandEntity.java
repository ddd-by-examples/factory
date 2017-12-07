package pl.com.bottega.factory.demand.forecasting;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long level;
    @Column
    private Demand.Schema schema;

    @Column
    private Long adjustmentLevel;
    @Column
    private Demand.Schema adjustmentSchema;
    @Column
    private boolean adjustmentStrong;

    DemandEntity(ProductDemandEntity product, LocalDate date) {
        this.product = product;
        this.date = date;
    }

    Demand getDemand() {
        return Demand.ofNullable(level, schema);
    }

    Adjustment getAdjustment() {
        return Optional.ofNullable(adjustmentLevel)
                .map(level -> new Adjustment(
                        Demand.of(level, adjustmentSchema),
                        adjustmentStrong
                ))
                .orElse(null);
    }

    void setDemand(Demand demand) {
        if (demand == null) {
            setLevel(null);
            setSchema(null);
        } else {
            setLevel(demand.getLevel());
            setSchema(demand.getSchema());
        }
    }

    void setAdjustment(Adjustment adjustment) {
        if (adjustment == null) {
            setAdjustmentLevel(null);
            setAdjustmentSchema(null);
            setAdjustmentStrong(false);
        } else {
            setAdjustmentLevel(adjustment.getDemand().getLevel());
            setAdjustmentSchema(adjustment.getDemand().getSchema());
            setAdjustmentStrong(adjustment.isStrong());
        }
    }

    DemandEntityId createId() {
        return new DemandEntityId(product.getRefNo(), date, id);
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
