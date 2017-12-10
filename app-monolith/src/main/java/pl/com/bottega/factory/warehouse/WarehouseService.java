package pl.com.bottega.factory.warehouse;

import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.shortages.prediction.calculation.Stock;

public interface WarehouseService {
    Stock forRefNo(RefNoId refNo);
}
