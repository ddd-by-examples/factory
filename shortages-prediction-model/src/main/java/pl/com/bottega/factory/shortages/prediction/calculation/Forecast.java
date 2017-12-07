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
        long level = stock.getLevel();

        Shortages.Builder found = Shortages.builder(refNo, stock.getLocked(), created);
        LocalDateTime lastTime = created;
        for (LocalDateTime time : times) {
            long demand = demands.get(time);
            long produced = outputs.getOutput(lastTime, time);

            long levelOnDelivery = level + produced - demand;

            if (levelOnDelivery < 0) {
                found.missing(time, levelOnDelivery);
            }
            level = Math.max(levelOnDelivery, 0);
            lastTime = time;
        }
        return found.build();
    }
}
