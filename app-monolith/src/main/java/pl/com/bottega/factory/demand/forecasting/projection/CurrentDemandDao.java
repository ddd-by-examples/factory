package pl.com.bottega.factory.demand.forecasting.projection;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionDao;

import java.time.LocalDate;
import java.util.List;

@Repository
@RepositoryRestResource(path = "demand-forecasts", collectionResourceRel = "demand-forecasts")
public interface CurrentDemandDao extends ProjectionDao<CurrentDemandEntity, Long> {

    @RestResource(exported = false)
    List<CurrentDemandEntity> findByRefNoAndDateGreaterThanEqual(String refNo, LocalDate date);

    @RestResource(exported = false)
    void deleteByRefNoAndDate(String refNo, LocalDate date);
}
