package pl.com.dddbyexamples.factory.demand.forecasting.command;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.com.dddbyexamples.tools.CommandRepository;

import java.time.LocalDate;

@Repository
@RepositoryRestResource(path = "demand-adjustments",
        collectionResourceRel = "demand-adjustments",
        itemResourceRel = "demand-adjustment")
public interface DemandAdjustmentDao extends CommandRepository<DemandAdjustmentEntity, Long> {
    @RestResource(exported = false)
    void deleteByCleanAfterGreaterThanEqual(LocalDate date);
}
