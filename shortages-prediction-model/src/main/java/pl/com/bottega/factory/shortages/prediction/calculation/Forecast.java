package pl.com.bottega.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.shortages.prediction.Shortages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class Forecast {

    String refNo;
    LocalDateTime created;
    List<LocalDateTime> times;
    CurrentStock stock;
    ProductionOutputs outputs;
    Demands demands;

    public Optional<Shortages> findShortages() {
        // TODO ASK including locked or only proper parts
        // TODO ASK current stock or on day start? what if we are in the middle of production a day?
        long level = stock.getLevel();

        Shortages.Builder found = Shortages.builder(refNo, stock.getLocked(), created);
        LocalDateTime lastTime = created;
        for (LocalDateTime time : times) {
            long demand = demands.get(time);
            long produced = outputs.getOutput(lastTime, time);

            long levelOnDelivery = level + produced - demand;

            if (levelOnDelivery < 0) {
                found.add(time, levelOnDelivery);
            }
            // TODO: ASK accumulated shortages or reset when under zero?
            level = levelOnDelivery >= 0 ? levelOnDelivery : 0;
            lastTime = time;
        }
        return found.build();
    }
}
