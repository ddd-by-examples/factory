package pl.com.dddbyexamples.factory.demand.forecasting.projection;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.dddbyexamples.factory.demand.forecasting.DailyId;
import pl.com.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged;

@Component
@AllArgsConstructor
public class CurrentDemandProjection {

    private final CurrentDemandDao demandDao;

    public void applyDemandedLevelsChanged(DemandedLevelsChanged event) {
        event.getResults().forEach(this::createOrUpdateDemand);
    }

    private void createOrUpdateDemand(DailyId daily, DemandedLevelsChanged.Change change) {
        CurrentDemandEntity currentDemandEntity = demandDao.findByRefNoAndDate(
                daily.getRefNo(),
                daily.getDate())
                .orElseGet(() -> new CurrentDemandEntity(
                        daily.getRefNo(),
                        daily.getDate(),
                        change.getCurrent().getLevel(),
                        change.getCurrent().getSchema()));
        currentDemandEntity.changeLevelTo(change.getCurrent().getLevel(), change.getCurrent().getSchema());
        demandDao.save(currentDemandEntity);
    }
}
