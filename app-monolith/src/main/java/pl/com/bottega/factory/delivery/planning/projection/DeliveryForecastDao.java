package pl.com.bottega.factory.delivery.planning.projection;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.ProjectionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource(path = "delivery-forecasts", collectionResourceRel = "delivery-forecasts")
public interface DeliveryForecastDao extends ProjectionRepository<DeliveryForecastEntity, Long> {

    @RestResource(path = "refNos", rel = "refNos")
    List<DeliveryForecastEntity> findByRefNoAndTimeGreaterThanEqual(String refNo, LocalDateTime from);

    @RestResource(exported = false)
    void deleteByRefNoAndDate(String refNo, LocalDate date);

    @RestResource(exported = false)
    void deleteByRefNo(String refNo);

}
