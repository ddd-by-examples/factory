package pl.com.bottega.factory.demand.forecasting.projection;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionDao;

import java.time.LocalDate;
import java.util.List;

@Repository
@RepositoryRestResource(
        path = "demand/forecasts",
        collectionResourceRel = "forecast of customers demands")
public interface CurrentDemandDao extends ProjectionDao<CurrentDemandEntity, Long> {

    List<CurrentDemandEntity> findByRefNoAndDateGreaterThanEqual(String refNo, LocalDate date);

    @RestResource(exported = false)
    void deleteByRefNoAndDate(String refNo, LocalDate date);
}
