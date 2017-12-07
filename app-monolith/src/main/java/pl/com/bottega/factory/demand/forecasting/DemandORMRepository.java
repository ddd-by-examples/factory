package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.com.bottega.factory.demand.forecasting.DailyDemand.DemandUpdated;
import pl.com.bottega.factory.demand.forecasting.DemandEntity.DemandEntityId;
import pl.com.bottega.factory.demand.forecasting.persistence.DemandDao;
import pl.com.bottega.factory.demand.forecasting.persistence.ProductDemandDao;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.tools.TechnicalId;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Component
@AllArgsConstructor
class DemandORMRepository {

    private Clock clock;
    private DemandEventsMapping events;
    private EntityManager em;
    private ProductDemandDao rootDao;
    private DemandDao demandDao;

    ProductDemand get(String refNo) {
        ProductDemandEntity root = rootDao.findByRefNo(refNo);
        RefNoId id = root.createId();

        Map<LocalDate, DemandEntity> data =
                demandDao.findByProductRefNoAndDateGreaterThanEqual(refNo, LocalDate.now(clock)).stream()
                        .collect(toMap(
                                DemandEntity::getDate,
                                Function.identity()
                        ));

        Demands demands = new Demands();
        demands.fetch = date -> map(refNo, date, data, demands);

        return new ProductDemand(id, demands, clock, events);
    }

    private DailyDemand map(String refNo, LocalDate date,
                            Map<LocalDate, DemandEntity> data,
                            Demands demands) {
        return ofNullable(data.get(date))
                .map(entity -> new DailyDemand(
                        entity.createId(),
                        demands,
                        entity.getDemand(),
                        entity.getAdjustment()))
                .orElseGet(() -> new DailyDemand(
                        new DemandEntityId(refNo, date),
                        demands,
                        null,
                        null
                ));
    }

    void save(ProductDemand model) {
        ProductDemandEntity root = rootDao.findOne(TechnicalId.get(model.id));
        if (model.demands.getUpdates().size() > 0) {
            em.lock(root, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        }
        for (DemandUpdated updated : model.demands.getUpdates()) {
            DemandEntity entity;
            if (TechnicalId.isPersisted(updated.getId())) {
                entity = demandDao.getOne(TechnicalId.get(updated.getId()));
            } else {
                entity = new DemandEntity(root, updated.getId().getDate());
            }
            entity.setDemand(updated.getDocumented());
            entity.setAdjustment(updated.getAdjustment());

            if (!TechnicalId.isPersisted(updated.getId())) {
                demandDao.save(entity);
            }
        }
    }
}
