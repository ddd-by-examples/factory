package pl.com.bottega.factory.demand.forecasting.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.bottega.factory.demand.forecasting.DemandEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemandDao extends JpaRepository<DemandEntity, Long> {

    List<DemandEntity> findByProductRefNoAndDateGreaterThanEqual(String refNo, LocalDate now);

}
