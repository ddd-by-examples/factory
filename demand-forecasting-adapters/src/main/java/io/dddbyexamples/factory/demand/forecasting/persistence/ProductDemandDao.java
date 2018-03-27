package io.dddbyexamples.factory.demand.forecasting.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RestResource(exported = false)
public interface ProductDemandDao extends JpaRepository<ProductDemandEntity, Long> {
    Optional<ProductDemandEntity> findByRefNo(String refNo);
}
