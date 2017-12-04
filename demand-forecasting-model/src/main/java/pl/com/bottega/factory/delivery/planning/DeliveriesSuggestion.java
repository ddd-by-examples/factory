package pl.com.bottega.factory.delivery.planning;

import pl.com.bottega.factory.demand.forecasting.Demand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

interface DeliveriesSuggestion {

    DeliveriesSuggestion DUMMY = (refNo, date, demand) ->
            Stream.of(new Delivery(refNo, date.atStartOfDay(), demand.getLevel()));

    static DeliveriesSuggestion allAtTime(LocalTime time) {
        return (refNo, date, demand) ->
                Stream.of(new Delivery(refNo, date.atTime(time), demand.getLevel()));
    }

    static DeliveriesSuggestion twoAtTimes(LocalTime first, LocalTime second) {
        return (refNo, date, demand) ->
                Stream.of(
                        new Delivery(refNo, date.atTime(first), demand.getLevel() / 2),
                        new Delivery(refNo, date.atTime(second), demand.getLevel() - (demand.getLevel() / 2))
                );
    }

    Stream<Delivery> deliveriesFor(String refNo, LocalDate date, Demand demand);
}
