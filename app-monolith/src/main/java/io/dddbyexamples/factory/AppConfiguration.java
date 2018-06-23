package io.dddbyexamples.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.dddbyexamples.factory.shortages.prediction.calculation.Stock;
import io.dddbyexamples.factory.warehouse.WarehouseService;

import java.time.Clock;

@SpringBootApplication
@EnableScheduling
@EntityScan(
        basePackageClasses = {AppConfiguration.class, Jsr310JpaConverters.class}
)
public class AppConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(AppConfiguration.class, args);
    }

    @Bean
    public WarehouseService warehouseService() {
        // mocked facade for external service
        return refNo -> new Stock(1200, 700);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Configuration
    @ServiceScan
    @Profile("cloud")
    public class ServiceConfiguration {
    }
}
