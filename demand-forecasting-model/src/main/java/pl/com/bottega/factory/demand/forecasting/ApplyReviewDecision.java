package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

import java.util.Collections;

@Value
public class ApplyReviewDecision {
    ReviewRequired.ToReview review;
    ReviewDecision decision;

    boolean requireAdjustment() {
        return decision != ReviewDecision.IGNORE;
    }

    AdjustDemand toAdjustment() {
        return new AdjustDemand(review.getRefNo(),
                Collections.singletonMap(
                        review.getDate(),
                        Adjustment.weak(decision.toAdjustment(review))
                )
        );
    }

    public String getRefNo() {
        return review.getRefNo();
    }
}
