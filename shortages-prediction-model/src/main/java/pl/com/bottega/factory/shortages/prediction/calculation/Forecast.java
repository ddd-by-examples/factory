package pl.com.bottega.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.shortages.prediction.Shortages;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.SortedSet;

@AllArgsConstructor
public class Forecast {

    private final String refNo;
    private final LocalDateTime created;
    private final SortedSet<LocalDateTime> deliveryTimes;
    private final Stock stock;
    private final ProductionOutputs outputs;
    private final Deliveries deliveries;

    public Optional<Shortages> findShortages() {
        long level = stock.getLevel();

        Shortages.Builder found = Shortages.builder(refNo, stock.getLocked(), created);
        LocalDateTime lastTime = created;
        for (LocalDateTime time : deliveryTimes) {
            long demand = deliveries.get(time);
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
