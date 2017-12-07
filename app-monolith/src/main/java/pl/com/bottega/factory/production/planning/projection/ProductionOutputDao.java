package pl.com.bottega.factory.production.planning.projection;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionDao;

import java.time.Instant;
import java.util.List;

@Repository
@RepositoryRestResource(
        path = "production/planning/outputs",
        collectionResourceRel = "forecast of production outputs")
public interface ProductionOutputDao extends ProjectionDao<ProductionOutputEntity, Long> {

    List<ProductionOutputEntity> findByRefNoAndStartGreaterThanEqual(String refNo, Instant instant);
}
