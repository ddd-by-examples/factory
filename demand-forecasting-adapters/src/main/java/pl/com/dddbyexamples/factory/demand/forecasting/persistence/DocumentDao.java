package pl.com.dddbyexamples.factory.demand.forecasting.persistence;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.com.dddbyexamples.tools.CommandRepository;

@Repository("documentDao")
@RepositoryRestResource(path = "demand-documents",
        collectionResourceRel = "demand-documents",
        itemResourceRel = "demand-document")
public interface DocumentDao extends CommandRepository<DocumentEntity, Long> {

}
