package pl.com.dddbyexamples.factory.demand.forecasting.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.dddbyexamples.factory.product.management.RefNoId;
import pl.com.dddbyexamples.tools.TechnicalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ProductDemand")
@Table(schema = "demand_forecasting")
@NoArgsConstructor
public class ProductDemandEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;
    private String refNo;

    public ProductDemandEntity(String refNo) {
        this.refNo = refNo;
    }

    public ProductDemandEntityId createId() {
        return new ProductDemandEntityId(refNo, id);
    }

    @Getter
    static class ProductDemandEntityId extends RefNoId implements TechnicalId {

        private Long id;

        ProductDemandEntityId(String refNo, long id) {
            super(refNo);
            this.id = id;
        }
    }
}
