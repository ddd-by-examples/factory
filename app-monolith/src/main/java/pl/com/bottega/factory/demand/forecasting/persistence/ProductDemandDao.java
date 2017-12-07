package pl.com.bottega.factory.demand.forecasting.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.factory.demand.forecasting.ProductDemandEntity;

@Repository
@RestResource(exported = false)
public interface ProductDemandDao extends JpaRepository<ProductDemandEntity, Long> {

    ProductDemandEntity findById(Long id);

    ProductDemandEntity findByRefNo(String refNo);
}
