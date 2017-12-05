package pl.com.bottega.factory.product.management;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;

@Entity(name = "ProductDescription")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductDescriptionEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Convert(converter = DescriptionAsJson.class)
    ProductDescription description;

    public ProductDescriptionEntity(ProductDescription description) {
        this.description = description;
    }

    public static class DescriptionAsJson extends JsonConverter<ProductDescription> {
        public DescriptionAsJson() {
            super(ProductDescription.class);
        }
    }
}
