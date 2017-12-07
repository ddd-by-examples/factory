package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.demand.forecasting.DemandEvents;

@Component
@AllArgsConstructor
public class CurrentDemandProjection implements DemandEvents {

    private final CurrentDemandDao demandDao;

    @Override
    public void emit(DemandedLevelsChanged event) {
        event.getResults().forEach((daily, change) -> {
                    demandDao.deleteByRefNoAndDate(
                            daily.getRefNo(),
                            daily.getDate());
                    demandDao.save(new CurrentDemandEntity(
                            daily.getRefNo(),
                            daily.getDate(),
                            change.getCurrent().getLevel(),
                            change.getCurrent().getSchema())
                    );
                }
        );
    }
}
