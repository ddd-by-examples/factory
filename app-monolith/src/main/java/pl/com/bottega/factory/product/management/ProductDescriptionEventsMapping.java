package pl.com.bottega.factory.product.management;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RepositoryEventHandler
public class ProductDescriptionEventsMapping {

    @HandleAfterSave
    @HandleAfterCreate
    public void handle(ProductDescriptionEntity entity) {
    }
}
