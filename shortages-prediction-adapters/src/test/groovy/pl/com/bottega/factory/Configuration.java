package pl.com.bottega.factory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.shortages.prediction.calculation.Forecast;
import pl.com.bottega.factory.shortages.prediction.calculation.Forecasts;
import pl.com.bottega.factory.shortages.prediction.monitoring.NewShortage;
import pl.com.bottega.factory.shortages.prediction.monitoring.ShortageEvents;
import pl.com.bottega.factory.shortages.prediction.monitoring.ShortageSolved;

import java.time.Clock;

@SpringBootApplication
@EnableScheduling
@EntityScan(
        basePackageClasses = {Configuration.class, Jsr310JpaConverters.class}
)
public class Configuration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Forecasts forecasts() {
        return new ForecastsFake();
    }

    @Bean
    public ShortageEventsFake shortageEvents() {
        return new ShortageEventsFake();
    }

    private class ShortageEventsFake implements ShortageEvents {
        @Override
        public void emit(NewShortage event) {

        }

        @Override
        public void emit(ShortageSolved event) {

        }
    }

    private class ForecastsFake implements Forecasts {
        @Override
        public Forecast get(RefNoId refNo, int daysAhead) {
            return null;
        }
    }
}
