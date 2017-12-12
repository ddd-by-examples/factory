package pl.com.bottega.factory.demand.forecasting.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.bottega.factory.demand.forecasting.DemandEvents.ReviewRequested.ReviewNeeded;
import pl.com.bottega.factory.demand.forecasting.ReviewDecision;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Entity(name = "DemandReview")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DemandReviewEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String refNo;
    @Column
    private LocalDate date;
    @Column
    private Instant timestamp;
    @Column
    @Convert(converter = ReviewAsJson.class)
    private ReviewNeeded review;

    @Column
    @Enumerated(EnumType.STRING)
    private ReviewDecision decision;
    @Column
    @Setter
    private LocalDate cleanAfter;

    public DemandReviewEntity(Instant timestamp, ReviewNeeded review) {
        this.timestamp = timestamp;
        this.refNo = review.getId().getRefNo();
        this.date = review.getId().getDate();
        this.review = review;
    }

    public boolean decisionTaken() {
        return decision != null;
    }

    public static class ReviewAsJson extends JsonConverter<ReviewNeeded> {
        public ReviewAsJson() {
            super(ReviewNeeded.class);
        }
    }
}
