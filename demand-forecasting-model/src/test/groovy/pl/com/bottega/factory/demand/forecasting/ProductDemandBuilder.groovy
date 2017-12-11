package pl.com.bottega.factory.demand.forecasting

import pl.com.bottega.factory.product.management.RefNoId

import java.time.*

import static DemandedLevelsChanged.Change
import static pl.com.bottega.factory.demand.forecasting.DemandEvents.DemandedLevelsChanged

class ProductDemandBuilder {

    def refNo = "3009000"
    def unitOfWork = new UnitOfWork()
    def demands = new DemandsRepositoryFake(refNo, unitOfWork, clock)
    def clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    DemandEvents events

    ProductDemand demand(long ... levels) {
        def date = LocalDate.now(clock)
        for (long level : levels) {
            demands.demanded(date, level)
            date = date.plusDays(1)
        }
        new ProductDemand(new RefNoId(refNo), demands, unitOfWork, clock, events)
    }

    Document document(LocalDate date, long ... levels) {
        def created = date.atTime(OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC)).toInstant()
        SortedMap<LocalDate, Demand> results = new TreeMap<>()
        for (def level : levels) {
            results.put(date, Demand.of(level))
            date = date.plusDays(1)
        }
        new Document(created, refNo, results)
    }

    AdjustDemand adjustDemand(Map<LocalDate, Long> adjustments) {
        Map<LocalDate, Adjustment> results = new HashMap<>()
        adjustments.forEach { date, level ->
            results.put(date, Adjustment.week(Demand.of(level)))
        }
        new AdjustDemand(refNo, results)
    }

    DemandedLevelsChanged levelChanged(List<Long>... changes) {
        def date = LocalDate.now(clock)
        Map<DailyId, Change> results = new HashMap<>()
        for (def change : changes) {
            if (change.size() == 2) {
                results.put(new DailyId(refNo, date), new Change(
                        Demand.of(change[0]),
                        Demand.of(change[1])))
            }
            date = date.plusDays(1)
        }
        new DemandedLevelsChanged(new RefNoId(refNo), results)
    }

    void clearUnitOfWork() {
        unitOfWork.@changes.clear()
        unitOfWork.@reviews.clear()
        unitOfWork.@updates.clear()
    }
}
