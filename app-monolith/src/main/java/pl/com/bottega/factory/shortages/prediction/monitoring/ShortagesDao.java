package pl.com.bottega.factory.shortages.prediction.monitoring;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionDao;

@Repository
@RepositoryRestResource(
        path = "shortages/prediction/current",
        collectionResourceRel = "predicted shortages")
public interface ShortagesDao extends ProjectionDao<ShortagesEntity, String> {
}
