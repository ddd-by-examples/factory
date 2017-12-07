package pl.com.bottega.factory.delivery.planning.definition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(
        path = "delivery/planning/definitions",
        collectionResourceRel = "definitions for auto delivery planning")
public interface DeliveryPlannerDefinitionDao extends JpaRepository<DeliveryPlannerDefinitionEntity, String> {
}
