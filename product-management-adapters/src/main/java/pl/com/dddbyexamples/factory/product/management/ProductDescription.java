package pl.com.dddbyexamples.factory.product.management;

import lombok.Value;

import java.util.List;

@Value
public class ProductDescription {
    String matNum;
    List<String> names;
}
