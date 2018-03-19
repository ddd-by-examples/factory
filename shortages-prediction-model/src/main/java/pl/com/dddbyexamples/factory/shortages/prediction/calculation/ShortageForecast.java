package pl.com.dddbyexamples.factory.shortages.prediction.calculation;

import lombok.AllArgsConstructor;
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.SortedSet;

@AllArgsConstructor
public class ShortageForecast {

    private final String refNo;
    private final LocalDateTime created;
    private final SortedSet<LocalDateTime> deliveryTimes;
    private final Stock stock;
    private final ProductionOutputs outputs;
    private final DeliveriesForecast deliveries;

    public Optional<Shortage> findShortages() {
        long level = stock.getLevel();

        Shortage.Builder found = Shortage.builder(refNo, stock.getLocked(), created);
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
