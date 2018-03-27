package io.dddbyexamples.factory.delivery.planning.projection;

import io.dddbyexamples.tools.ProjectionRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource(path = "delivery-forecasts",
        collectionResourceRel = "delivery-forecasts",
        itemResourceRel = "delivery-forecast")
public interface DeliveryForecastDao extends ProjectionRepository<DeliveryForecastEntity, Long> {

    @RestResource(path = "refNos", rel = "refNos")
    List<DeliveryForecastEntity> findByRefNoAndTimeBetween(String refNo, LocalDateTime from, LocalDateTime to);

    @RestResource(exported = false)
    void deleteByRefNoAndDate(String refNo, LocalDate date);

    @RestResource(exported = false)
    void deleteByRefNo(String refNo);

}
