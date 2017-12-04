package pl.com.bottega.factory.demand.forecasting;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "ProductDemand")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductDemandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Version
    Long version;
    @Column
    String refNo;

}
