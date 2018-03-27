package io.dddbyexamples.factory.shortages.prediction.monitoring;

import io.dddbyexamples.factory.shortages.prediction.ConfigurationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MonitoringConfiguration {

    @Autowired
    private ShortagePredictionProcessRepository repository;

    @Bean
    ShortagePredictionService shortagePredictionService() {
        return new ShortagePredictionService(repository);
    }

    @Bean
    ShortageDiffPolicy policy() {
        return ShortageDiffPolicy.ValuesAreNotSame;
    }

    @Bean
    ConfigurationParams configuration() {
        return () -> 14;
    }

}
