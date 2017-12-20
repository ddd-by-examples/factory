package pl.com.bottega.factory.product.management;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "ProductDescription")
@Table(schema = "product_management")
@Getter
@NoArgsConstructor
public class ProductDescriptionEntity implements Serializable {

    @Id
    private String refNo;
    @Convert(converter = DescriptionAsJson.class)
    private ProductDescription description;

    ProductDescriptionEntity(String refNo, ProductDescription description) {
        this.refNo = refNo;
        this.description = description;
    }

    public static class DescriptionAsJson extends JsonConverter<ProductDescription> {
        public DescriptionAsJson() {
            super(ProductDescription.class);
        }
    }
}
