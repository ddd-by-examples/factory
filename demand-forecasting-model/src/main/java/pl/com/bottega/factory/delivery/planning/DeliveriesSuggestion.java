package pl.com.bottega.factory.delivery.planning;

import pl.com.bottega.factory.demand.forecasting.Demand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Stream;

interface DeliveriesSuggestion {

    DeliveriesSuggestion DUMMY = (refNo, date, demand) ->
            Stream.of(new Delivery(refNo, date.atStartOfDay(), demand.getLevel()));

    static DeliveriesSuggestion timesAndFractions(Map<LocalTime, Double> timesAndFractions) {
        return (refNo, date, demand) ->
                timesAndFractions.entrySet().stream()
                        .map(e -> new Delivery(
                                refNo,
                                date.atTime(e.getKey()), ((long) ((double) demand.getLevel() / e.getValue())))
                        );
    }

    Stream<Delivery> deliveriesFor(String refNo, LocalDate date, Demand demand);
}
