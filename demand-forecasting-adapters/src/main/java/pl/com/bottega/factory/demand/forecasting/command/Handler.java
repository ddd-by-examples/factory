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
    private final DemandReviewDao reviews;
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

    @HandleBeforeSave
    public void review(DemandReviewEntity review) {
        if (review.decisionTaken()) {
            review.setCleanAfter(LocalDate.now(clock).plusDays(7));
            service.review(review.getReview(), review.getDecision());
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void clean() {
        adjustments.deleteByCleanAfterGreaterThanEqual(LocalDate.now(clock));
        reviews.deleteByCleanAfterGreaterThanEqual(LocalDate.now(clock));
    }
}
