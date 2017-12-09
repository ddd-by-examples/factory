package pl.com.bottega.factory.production.planning.projection;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionDao;

import java.time.LocalDate;
import java.util.List;

@Repository
@RepositoryRestResource(path = "production-outputs-daily", collectionResourceRel = "production-outputs-daily")
public interface ProductionDailyOutputDao extends ProjectionDao<ProductionDailyOutputEntity, Long> {

    List<ProductionDailyOutputEntity> findByRefNoAndDateGreaterThanEqual(String refNo, LocalDate date);
}
