package pl.com.bottega.factory.demand.forecasting;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.tools.TechnicalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ProductDemand")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductDemandEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Column
    private String refNo;

    public ProductDemandEntity(String refNo) {
        this.refNo = refNo;
    }

    ProductDemandEntityId createId() {
        return new ProductDemandEntityId(refNo, id);
    }

    @Getter
    static class ProductDemandEntityId extends RefNoId implements TechnicalId {

        Long id;

        ProductDemandEntityId(String refNo) {
            super(refNo);
        }

        ProductDemandEntityId(String refNo, long id) {
            super(refNo);
            this.id = id;
        }
    }
}
