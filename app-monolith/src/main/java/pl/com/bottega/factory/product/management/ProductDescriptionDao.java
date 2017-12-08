package pl.com.bottega.factory.product.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

@RepositoryRestResource(
        path = "product/management/descriptions",
        collectionResourceRel = "descriptions of products")
public interface ProductDescriptionDao extends JpaRepository<ProductDescriptionEntity, String> {

    @RestResource(exported = false)
    default Optional<ProductDescription> description(String refNo) {
        return Optional.ofNullable(findOne(refNo))
                .map(ProductDescriptionEntity::getDescription);
    }
}
