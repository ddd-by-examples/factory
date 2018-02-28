package pl.com.bottega.factory.product.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
@RepositoryRestResource(
        path = "product-descriptions",
        collectionResourceRel = "product-descriptions",
        itemResourceRel = "product-description")
public interface ProductDescriptionDao extends JpaRepository<ProductDescriptionEntity, String> {

}
