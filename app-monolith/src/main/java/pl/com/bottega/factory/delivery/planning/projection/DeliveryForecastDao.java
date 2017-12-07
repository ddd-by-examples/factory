package pl.com.bottega.factory.delivery.planning.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryForecastDao extends JpaRepository<DeliveryForecastEntity, Long> {

    List<DeliveryForecastEntity> findByRefNoAndDateGreaterThanEqual(String refNo, Instant instant);
    void deleteByRefNoAndDate(String refNo, LocalDate date);
    void deleteByRefNo(String refNo);
}
