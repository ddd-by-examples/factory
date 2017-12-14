package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.ReviewRequested.ReviewNeeded;

import java.util.Collections;
import java.util.function.Function;

@AllArgsConstructor
public enum ReviewDecision {
    IGNORE(r -> null),
    PICK_PREVIOUS(ReviewNeeded::getPreviousDocumented),
    MAKE_ADJUSTMENT_WEAK(ReviewNeeded::getAdjustment),
    PICK_NEW(ReviewNeeded::getNewDocumented);

    private final Function<ReviewNeeded, Demand> pick;

    public AdjustDemand toAdjustment(ReviewNeeded review) {
        if (this == IGNORE) {
            throw new IllegalStateException("can't convert " + this + " to adjustment");
        }
        return new AdjustDemand(review.getRefNo(),
                Collections.singletonMap(
                        review.getDate(),
                        Adjustment.weak(pick.apply(review))
                )
        );
    }

    public boolean requireAdjustment() {
        return this != IGNORE;
    }
}
