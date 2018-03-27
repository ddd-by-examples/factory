package io.dddbyexamples.factory.demand.forecasting.command;

import io.dddbyexamples.factory.demand.forecasting.ApplyReviewDecision;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.dddbyexamples.factory.demand.forecasting.ReviewDecision;
import io.dddbyexamples.factory.demand.forecasting.ReviewRequired.ToReview;
import io.dddbyexamples.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Entity(name = "RequiredReview")
@Table(schema = "demand_forecasting")
@Getter
@NoArgsConstructor
public class RequiredReviewEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    private LocalDate date;
    private Instant timestamp;
    @Convert(converter = ReviewAsJson.class)
    private ToReview review;

    @Enumerated(EnumType.STRING)
    private ReviewDecision decision;
    @Setter
    private LocalDate cleanAfter;

    public RequiredReviewEntity(Instant timestamp, ToReview review) {
        this.timestamp = timestamp;
        this.refNo = review.getId().getRefNo();
        this.date = review.getId().getDate();
        this.review = review;
    }

    public boolean decisionTaken() {
        return decision != null;
    }

    public ApplyReviewDecision getReviewDecision() {
        return new ApplyReviewDecision(review, decision);
    }

    public static class ReviewAsJson extends JsonConverter<ToReview> {
        public ReviewAsJson() {
            super(ToReview.class);
        }
    }
}
