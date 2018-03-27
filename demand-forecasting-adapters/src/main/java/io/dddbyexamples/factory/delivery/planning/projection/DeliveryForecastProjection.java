package io.dddbyexamples.factory.delivery.planning.projection;

import io.dddbyexamples.factory.delivery.planning.DeliveryAutoPlanner;
import io.dddbyexamples.factory.delivery.planning.DeliveryAutoPlannerORMRepository;
import io.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged;
import io.dddbyexamples.factory.demand.forecasting.projection.CurrentDemandDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import io.dddbyexamples.factory.demand.forecasting.Demand;
import io.dddbyexamples.factory.demand.forecasting.projection.CurrentDemandEntity;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DeliveryForecastProjection {

    private final Clock clock;
    private final DeliveryForecastDao forecastDao;
    private final CurrentDemandDao demandDao;
    private final DeliveryAutoPlannerORMRepository planners;

    public void applyDemandedLevelsChanged(DemandedLevelsChanged event) {
        DeliveryAutoPlanner planner = planners.get(event.getRefNo().getRefNo());
        event.getResults().keySet()
                .forEach(daily -> forecastDao.deleteByRefNoAndDate(
                        daily.getRefNo(),
                        daily.getDate())
                );
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

    public void applyDeliveryPlannerDefinitionChange(String refNo) {
        List<CurrentDemandEntity> demands = demandDao.findByRefNoAndDateGreaterThanEqual(refNo, LocalDate.now(clock));
        DeliveryAutoPlanner planner = planners.get(refNo);
        forecastDao.deleteByRefNo(refNo);
        demands.stream()
                .flatMap(entry -> planner.propose(
                        entry.getDate(),
                        Demand.of(entry.getLevel(), entry.getSchema())))
                .forEach(delivery ->
                        forecastDao.save(new DeliveryForecastEntity(
                                delivery.getRefNo(),
                                delivery.getTime(),
                                delivery.getLevel())
                        )
                );
    }
}
