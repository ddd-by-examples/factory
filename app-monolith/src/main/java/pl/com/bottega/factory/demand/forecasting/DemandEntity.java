package pl.com.bottega.factory.demand.forecasting;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "Demand")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DemandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    ProductDemandEntity product;
    @Column
    LocalDate date;

}
