package pl.com.bottega.factory.production.planning.projection;

import java.time.Instant;
import java.util.List;

public interface ProductionOutputDao {

    void save(ProductionOutputEntity entity);

    List<ProductionOutputEntity> findRefNoFrom(String refNo, Instant instant);
}
