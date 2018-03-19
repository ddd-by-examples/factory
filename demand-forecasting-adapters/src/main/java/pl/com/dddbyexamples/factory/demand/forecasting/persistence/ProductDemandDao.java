package pl.com.dddbyexamples.factory.demand.forecasting.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface ProductDemandDao extends JpaRepository<ProductDemandEntity, Long> {
    ProductDemandEntity findByRefNo(String refNo);
}
