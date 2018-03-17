package pl.com.dddbyexamples.factory.demand.forecasting.projection;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged;

@Component
@AllArgsConstructor
public class CurrentDemandProjection {

    private final CurrentDemandDao demandDao;

    public void applyDemandedLevelsChanged(DemandedLevelsChanged event) {
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
