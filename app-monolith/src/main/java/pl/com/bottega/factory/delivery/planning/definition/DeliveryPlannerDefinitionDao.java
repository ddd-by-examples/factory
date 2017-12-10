package pl.com.bottega.factory.delivery.planning.definition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(
        path = "delivery-definitions", collectionResourceRel = "delivery-definitions")
public interface DeliveryPlannerDefinitionDao extends JpaRepository<DeliveryPlannerDefinitionEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    DeliveryPlannerDefinitionEntity findByRefNo(String refNo);
}
