package pl.com.bottega.factory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

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
}
