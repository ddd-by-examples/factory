package pl.com.bottega.factory.product.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(
        path = "product-descriptions", collectionResourceRel = "product-descriptions")
public interface ProductDescriptionDao extends JpaRepository<ProductDescriptionEntity, Long> {
    @RestResource(path = "refNos", rel = "refNos")
    ProductDescriptionEntity findByRefNo(String refNo);
}
