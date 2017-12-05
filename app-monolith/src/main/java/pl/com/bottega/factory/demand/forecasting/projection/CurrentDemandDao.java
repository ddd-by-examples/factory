package pl.com.bottega.factory.demand.forecasting.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrentDemandDao extends JpaRepository<CurrentDemandEntity, Long> {

    List<CurrentDemandEntity> findByRefNoAndDateGreaterThanEqual(String refNo, LocalDate date);
}
