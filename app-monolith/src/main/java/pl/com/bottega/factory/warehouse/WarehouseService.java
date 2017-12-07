package pl.com.bottega.factory.warehouse;

import pl.com.bottega.factory.shortages.prediction.calculation.CurrentStock;

public interface WarehouseService {
    CurrentStock forRefNo(String refNo);
}
