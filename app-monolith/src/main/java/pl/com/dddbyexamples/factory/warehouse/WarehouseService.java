package pl.com.dddbyexamples.factory.warehouse;

import pl.com.dddbyexamples.factory.product.management.RefNoId;
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.Stock;

public interface WarehouseService {
    Stock forRefNo(RefNoId refNo);
}
