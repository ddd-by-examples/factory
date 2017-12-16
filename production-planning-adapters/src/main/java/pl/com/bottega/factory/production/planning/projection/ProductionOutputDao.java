package pl.com.bottega.factory.production.planning.projection;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource(path = "production-outputs",
        collectionResourceRel = "production-outputs",
        itemResourceRel = "production-output")
public interface ProductionOutputDao extends ProjectionRepository<ProductionOutputEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    List<ProductionOutputEntity> findByRefNoAndEndGreaterThanAndStartLessThan(String refNo, LocalDateTime from, LocalDateTime to);
}
