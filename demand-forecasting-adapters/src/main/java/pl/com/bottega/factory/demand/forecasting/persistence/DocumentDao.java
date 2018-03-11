package pl.com.bottega.factory.demand.forecasting.persistence;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.com.bottega.tools.CommandRepository;

@Repository("documentDao")
@RepositoryRestResource(path = "demand-documents",
        collectionResourceRel = "demand-documents",
        itemResourceRel = "demand-document")
public interface DocumentDao extends CommandRepository<DocumentEntity, Long> {

}
