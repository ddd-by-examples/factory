package pl.com.bottega.factory.shortages.prediction.monitoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortagesDao extends JpaRepository<ShortagesEntity, String> {
}
