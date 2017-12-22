package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;

import java.util.Collections;
import java.util.function.Function;

@AllArgsConstructor
public enum ReviewDecision {
    IGNORE(r -> null),
    PICK_PREVIOUS(ToReview::getPreviousDocumented),
    MAKE_ADJUSTMENT_WEAK(ToReview::getAdjustment),
    PICK_NEW(ToReview::getNewDocumented);

    private final Function<ToReview, Demand> pick;

    public AdjustDemand toAdjustment(ToReview review) {
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
