package io.dddbyexamples.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import io.dddbyexamples.factory.demand.forecasting.ReviewRequired.ToReview;

import java.util.function.Function;

@AllArgsConstructor
public enum ReviewDecision {
    IGNORE(r -> null),
    PICK_PREVIOUS(ToReview::getPreviousDocumented),
    MAKE_ADJUSTMENT_WEAK(ToReview::getAdjustment),
    PICK_NEW(ToReview::getNewDocumented);

    private final Function<ToReview, Demand> pick;

    public Demand toAdjustment(ToReview review) {
        if (this == ReviewDecision.IGNORE) {
            throw new IllegalStateException("can't convert " + this + " to adjustment");
        }
        return pick.apply(review);
    }
}
