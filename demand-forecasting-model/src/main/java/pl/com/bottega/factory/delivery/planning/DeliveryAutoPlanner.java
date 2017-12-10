package pl.com.bottega.factory.delivery.planning;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.Demand;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

@AllArgsConstructor
public class DeliveryAutoPlanner {
    private String refNo;
    private Map<Demand.Schema, DeliveriesSuggestion> policies;

    public Stream<Delivery> propose(LocalDate date, Demand demand) {
        return policies.getOrDefault(demand.getSchema(), DeliveriesSuggestion.DUMMY)
                .deliveriesFor(refNo, date, demand);
    }
}
