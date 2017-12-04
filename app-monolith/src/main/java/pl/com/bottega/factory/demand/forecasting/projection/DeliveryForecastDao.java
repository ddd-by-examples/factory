package pl.com.bottega.factory.demand.forecasting.projection;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface DeliveryForecastDao {

    void save(DeliveryForecastEntity entity);

    List<DeliveryForecastEntity> findRefNoFrom(String refNo, Instant instant, int daysAhead);

    void delete(String refNo, LocalDate date);
}
