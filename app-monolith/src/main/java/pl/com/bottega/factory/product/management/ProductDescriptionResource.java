package pl.com.bottega.factory.product.management;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductDescriptionResource extends PagingAndSortingRepository<ProductDescriptionEntity, Long> {
}
