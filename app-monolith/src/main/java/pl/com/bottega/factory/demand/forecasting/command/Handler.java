package pl.com.bottega.factory.demand.forecasting.command;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.factory.demand.forecasting.DemandService;

import java.time.Clock;
import java.time.LocalDate;

@Component
@AllArgsConstructor
@RepositoryEventHandler
public class Handler {

    private final DemandService service;
    private final DemandAdjustmentDao adjustments;
    private final Clock clock;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void adjustDemand(DemandAdjustmentEntity resource) {
        LocalDate latest = resource.getAdjustment()
                .latestAdjustment()
                .orElse(LocalDate.now(clock));
        resource.setCleanAfter(latest.plusDays(7));
        service.adjust(resource.getAdjustment());
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void clean() {
        adjustments.deleteByCleanAfterGreaterThanEqual(LocalDate.now(clock));
    }
}
