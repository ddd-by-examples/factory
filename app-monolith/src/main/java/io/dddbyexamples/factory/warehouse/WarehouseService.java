package io.dddbyexamples.factory.warehouse;

import io.dddbyexamples.factory.shortages.prediction.calculation.Stock;
import io.dddbyexamples.factory.product.management.RefNoId;

public interface WarehouseService {
    Stock forRefNo(RefNoId refNo);
}
