package pl.com.dddbyexamples.factory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.com.dddbyexamples.factory.product.management.RefNoId;
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.ShortageForecast;
import pl.com.dddbyexamples.factory.shortages.prediction.calculation.ShortageForecasts;
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.NewShortage;
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.ShortageEvents;
import pl.com.dddbyexamples.factory.shortages.prediction.monitoring.ShortageSolved;

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
    public ShortageForecasts forecasts() {
        return new ShortageForecastsFake();
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

    private class ShortageForecastsFake implements ShortageForecasts {
        @Override
        public ShortageForecast get(RefNoId refNo, int daysAhead) {
            return null;
        }
    }
}
