package pl.com.bottega.factory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.com.bottega.factory.demand.forecasting.DemandEvents;
import pl.com.bottega.factory.demand.forecasting.DemandedLevelsChanged;
import pl.com.bottega.factory.demand.forecasting.ReviewRequested;

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
    public DemandEventsFake DemandEvents() {
        return new DemandEventsFake();
    }

    private class DemandEventsFake implements DemandEvents {
        @Override
        public void emit(DemandedLevelsChanged event) {

        }

        @Override
        public void emit(ReviewRequested event) {

        }
    }
}
