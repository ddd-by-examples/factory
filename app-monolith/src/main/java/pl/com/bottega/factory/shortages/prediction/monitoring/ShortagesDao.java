package pl.com.bottega.factory.shortages.prediction.monitoring;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionRepository;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "shortages", collectionResourceRel = "shortages")
public interface ShortagesDao extends ProjectionRepository<ShortagesEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    Optional<ShortagesEntity> findByRefNo(String refNo);
}
