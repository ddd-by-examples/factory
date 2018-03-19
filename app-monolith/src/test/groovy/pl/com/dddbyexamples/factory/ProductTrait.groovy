package pl.com.dddbyexamples.factory

import pl.com.dddbyexamples.factory.demand.forecasting.AdjustDemand
import pl.com.dddbyexamples.factory.demand.forecasting.Adjustment
import pl.com.dddbyexamples.factory.demand.forecasting.Demand
import pl.com.dddbyexamples.factory.demand.forecasting.Document
import pl.com.dddbyexamples.factory.demand.forecasting.command.DemandAdjustmentEntity
import pl.com.dddbyexamples.factory.demand.forecasting.persistence.DocumentEntity
import pl.com.dddbyexamples.factory.product.management.ProductDescription
import pl.com.dddbyexamples.factory.product.management.ProductDescriptionEntity

import java.time.Instant
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset

trait ProductTrait {

    DocumentEntity documentFor(String refNo, LocalDate date, long ... levels) {
        Document document = document(refNo, date, levels)
        return new DocumentEntity("uri", "storedUri", document)
    }

    ProductDescriptionEntity productDescription(String refNo) {
        ProductDescription desc = new ProductDescription("461952398951", ["PROWAD.POJ.NA JARZ.ESSENT"])
        return new ProductDescriptionEntity(refNo, desc)
    }

    Document document(String refNo, LocalDate date, long ... levels) {
        Instant created = date.atTime(OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC)).toInstant()
        SortedMap<LocalDate, Demand> results = new TreeMap<>()
        for (long level : levels) {
            results.put(date, Demand.of(level))
            date = date.plusDays(1)
        }
        return new Document(created, refNo, results)
    }

    DemandAdjustmentEntity strongAdjustment(String refNo, Map<LocalDate, Adjustment> adjustments) {
        return new DemandAdjustmentEntity(refNo, "agent", new AdjustDemand(refNo, adjustments))

    }
}
