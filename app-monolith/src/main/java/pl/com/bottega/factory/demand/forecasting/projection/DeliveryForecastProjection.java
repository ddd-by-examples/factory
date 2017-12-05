package pl.com.bottega.factory.demand.forecasting.projection;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.delivery.planning.DeliveryAutoPlanner;
import pl.com.bottega.factory.delivery.planning.DeliveryAutoPlannerRepository;
import pl.com.bottega.factory.demand.forecasting.DemandEvents;

@Component
@AllArgsConstructor
public class DeliveryForecastProjection implements DemandEvents {

    DeliveryForecastDao forecastDao;
    DeliveryAutoPlannerRepository planners;

    @Override
    public void emit(DemandedLevelsChanged event) {
        event.getResults().keySet()
                .forEach(daily -> forecastDao.deleteByRefNoAndDate(
                        daily.getRefNo(),
                        daily.getDate())
                );

        DeliveryAutoPlanner planner = planners.get(event.getId());
        event.getResults().entrySet().stream()
                .flatMap(entry -> planner.propose(
                        entry.getKey().getDate(),
                        entry.getValue().getCurrent()))
                .forEach(delivery ->
                        forecastDao.save(new DeliveryForecastEntity(
                                delivery.getRefNo(),
                                delivery.getTime(),
                                delivery.getLevel())
                        )
                );
    }
}
