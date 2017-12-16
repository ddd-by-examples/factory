package pl.com.bottega.factory.product.management;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ProductDescription")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "refNo")
public class ProductDescriptionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String refNo;
    @Column
    @Convert(converter = DescriptionAsJson.class)
    ProductDescription description;

    public ProductDescriptionEntity(String refNo, ProductDescription description) {
        this.refNo = refNo;
        this.description = description;
    }

    public static class DescriptionAsJson extends JsonConverter<ProductDescription> {
        public DescriptionAsJson() {
            super(ProductDescription.class);
        }
    }
}
