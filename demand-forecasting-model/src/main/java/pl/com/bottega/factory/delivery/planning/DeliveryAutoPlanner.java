package pl.com.bottega.factory.delivery.planning;

import pl.com.bottega.factory.demand.forecasting.Demand;
import lombok.AllArgsConstructor;

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
