package pl.com.bottega.factory.demand.forecasting.command;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.CommandRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RepositoryRestResource(
        path = "demand-reviews",
        collectionResourceRel = "demand-reviews",
        itemResourceRel = "demand-review")
public interface DemandReviewDao extends CommandRepository<DemandReviewEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    List<DemandReviewEntity> findByRefNoAndDecisionIsNull(String refNo);

    @RestResource(exported = false)
    void deleteByCleanAfterGreaterThanEqual(LocalDate date);
}
