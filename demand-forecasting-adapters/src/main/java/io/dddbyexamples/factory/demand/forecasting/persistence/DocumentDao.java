package io.dddbyexamples.factory.demand.forecasting.persistence;

import io.dddbyexamples.tools.CommandRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository("documentDao")
@RepositoryRestResource(path = "demand-documents",
        collectionResourceRel = "demand-documents",
        itemResourceRel = "demand-document")
public interface DocumentDao extends CommandRepository<DocumentEntity, Long> {

}
