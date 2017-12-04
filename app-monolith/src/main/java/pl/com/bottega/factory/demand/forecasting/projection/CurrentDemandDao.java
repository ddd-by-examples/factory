package pl.com.bottega.factory.demand.forecasting.projection;

import java.time.Instant;
import java.util.List;

public interface CurrentDemandDao {

    void save(CurrentDemandEntity entity);

    List<CurrentDemandEntity> findRefNoFromDate(String refNo, Instant now);
}
