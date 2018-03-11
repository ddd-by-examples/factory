package pl.com.bottega.factory.demand.forecasting.command;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.factory.demand.forecasting.DemandService;
import pl.com.bottega.factory.demand.forecasting.persistence.DocumentEntity;

import java.time.Clock;
import java.time.LocalDate;

@Component
@Transactional
@AllArgsConstructor
@RepositoryEventHandler
public class CommandsHandler {

    private final DemandService service;
    private final DemandAdjustmentDao adjustments;
    private final RequiredReviewDao reviews;
    private final Clock clock;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void adjust(DemandAdjustmentEntity adjustment) {
        LocalDate latest = adjustment.getAdjustment()
                .latestAdjustment()
                .orElse(LocalDate.now(clock));
        adjustment.setCleanAfter(latest.plusDays(7));
        service.adjust(adjustment.getAdjustment());
    }

    @HandleAfterCreate
    public void process(DocumentEntity document) {
        service.process(document.getDocument());
    }

    @HandleBeforeSave
    public void review(RequiredReviewEntity review) {
        if (review.decisionTaken()) {
            review.setCleanAfter(LocalDate.now(clock).plusDays(7));
            service.review(review.getReviewDecision());
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @EventListener(ApplicationReadyEvent.class)
    public void clean() {
        adjustments.deleteByCleanAfterGreaterThanEqual(LocalDate.now(clock));
        reviews.deleteByCleanAfterGreaterThanEqual(LocalDate.now(clock));
    }
}
