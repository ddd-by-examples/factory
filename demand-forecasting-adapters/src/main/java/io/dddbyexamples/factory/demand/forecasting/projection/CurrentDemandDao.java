package io.dddbyexamples.factory.demand.forecasting.projection;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import io.dddbyexamples.tools.ProjectionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(path = "demand-forecasts",
        collectionResourceRel = "demand-forecasts",
        itemResourceRel = "demand-forecast")
public interface CurrentDemandDao extends ProjectionRepository<CurrentDemandEntity, Long> {
    @Deprecated
    @RestResource(path = "refNos", rel = "refNos")
    List<CurrentDemandEntity> findByRefNoAndDateGreaterThanEqual(@Param("refNo") String refNo, @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate date);

    @RestResource(path = "byDate")
    List<CurrentDemandEntity> findByDate(LocalDate date);

    @RestResource(exported = false)
    Optional<CurrentDemandEntity> findByRefNoAndDate(String refNo, LocalDate date);
}
