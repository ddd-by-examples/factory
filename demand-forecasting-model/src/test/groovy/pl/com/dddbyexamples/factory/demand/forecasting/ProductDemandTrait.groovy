package pl.com.dddbyexamples.factory.demand.forecasting

import pl.com.dddbyexamples.factory.demand.forecasting.AdjustDemand
import pl.com.dddbyexamples.factory.demand.forecasting.ApplyReviewDecision
import pl.com.dddbyexamples.factory.demand.forecasting.DemandedLevelsChanged
import pl.com.dddbyexamples.factory.demand.forecasting.Document
import pl.com.dddbyexamples.factory.demand.forecasting.ReviewDecision
import pl.com.dddbyexamples.factory.demand.forecasting.ReviewRequired

import java.time.LocalDate

import static pl.com.dddbyexamples.factory.demand.forecasting.ReviewRequired.ToReview

trait ProductDemandTrait {

    ProductDemandBuilder builder

    ProductDemand demanded(long ... levels) {
        builder.demand(levels).build()
    }

    ProductDemandBuilder demand(long ... levels) {
        builder.demand(levels)
    }

    Document document(LocalDate date, long ... levels) {
        builder.document(date, levels)
    }

    AdjustDemand adjustments(Map<LocalDate, Long> map) {
        builder.adjustDemand(map)
    }

    DemandedLevelsChanged levelChanged(List<Long>... changes) {
        builder.levelChanged(changes)
    }

    List<Long> notChanged() {
        []
    }

    ReviewRequired reviewRequest(ToReview... reviews) {
        builder.reviewRequest(reviews)
    }

    ToReview review(
            LocalDate date,
            long previousDocumented,
            long strongAdjustment,
            long newDocumented) {
        return builder.review(date, previousDocumented, strongAdjustment, newDocumented)
    }

    ApplyReviewDecision reviewDecision(ToReview review, ReviewDecision decision) {
        builder.reviewDecision(review, decision)
    }
}