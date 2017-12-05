package pl.com.bottega.factory.demand.forecasting.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DeliveryForecastDao extends JpaRepository<DeliveryForecastEntity, Long> {

    void deleteByRefNoAndDate(String refNo, LocalDate date);
}
