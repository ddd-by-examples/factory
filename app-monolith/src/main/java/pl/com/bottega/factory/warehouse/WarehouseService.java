package pl.com.bottega.factory.warehouse;

import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.shortages.prediction.calculation.CurrentStock;

public interface WarehouseService {
    CurrentStock forRefNo(RefNoId refNo);
}
