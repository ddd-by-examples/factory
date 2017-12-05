package pl.com.bottega.factory.product.management;

import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProductDescriptionEventsMapping {

    @HandleAfterSave
    public void handle(ProductDescriptionEntity entity) {
    }
}
