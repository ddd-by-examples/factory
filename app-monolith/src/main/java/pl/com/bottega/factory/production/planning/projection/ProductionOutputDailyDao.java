package pl.com.bottega.factory.production.planning.projection;

import java.time.Instant;
import java.util.List;

public interface ProductionOutputDailyDao {

    void save(ProductionOutputDailyEntity entity);

    List<ProductionOutputDailyEntity> findRefNoFromDate(String refNo, Instant now);
}
