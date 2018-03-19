package pl.com.dddbyexamples.factory.demand.forecasting.command;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.dddbyexamples.tools.CommandRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RepositoryRestResource(
        path = "required-reviews",
        collectionResourceRel = "required-reviews",
        itemResourceRel = "required-review")
public interface RequiredReviewDao extends CommandRepository<RequiredReviewEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    List<RequiredReviewEntity> findByRefNoAndDecisionIsNull(String refNo);

    @RestResource(exported = false)
    void deleteByCleanAfterGreaterThanEqual(LocalDate date);
}
