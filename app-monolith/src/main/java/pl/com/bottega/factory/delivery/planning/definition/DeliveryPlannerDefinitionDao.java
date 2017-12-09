package pl.com.bottega.factory.delivery.planning.definition;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(
        path = "delivery-definitions", collectionResourceRel = "delivery-definitions")
public interface DeliveryPlannerDefinitionDao extends CrudRepository<DeliveryPlannerDefinitionEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    DeliveryPlannerDefinitionEntity findByRefNo(@Param("refNo") String refNo);
}
