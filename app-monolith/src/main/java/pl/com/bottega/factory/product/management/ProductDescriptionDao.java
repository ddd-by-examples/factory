package pl.com.bottega.factory.product.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionDao extends JpaRepository<ProductDescriptionEntity, Long> {
}
