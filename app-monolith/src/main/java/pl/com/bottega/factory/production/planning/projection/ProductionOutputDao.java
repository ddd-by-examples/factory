package pl.com.bottega.factory.production.planning.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ProductionOutputDao extends JpaRepository<ProductionOutputEntity, Long> {

    List<ProductionOutputEntity> findByRefNoAndStartGreaterThanEqual(String refNo, Instant instant);
}
