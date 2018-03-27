package io.dddbyexamples.factory.shortages.prediction.monitoring.persistence;

import io.dddbyexamples.tools.ProjectionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "shortages",
        collectionResourceRel = "shortages",
        itemResourceRel = "shortage")
public interface ShortagesDao extends ProjectionRepository<ShortagesEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    Optional<ShortagesEntity> findByRefNo(@Param("refNo") String refNo);

    void deleteAllInBatch();
}
