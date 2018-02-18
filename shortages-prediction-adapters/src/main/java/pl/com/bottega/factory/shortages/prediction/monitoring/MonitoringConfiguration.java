package pl.com.bottega.factory.shortages.prediction.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.bottega.factory.shortages.prediction.ConfigurationParams;

import javax.transaction.Transactional;

@Configuration
class MonitoringConfiguration {

    @Autowired
    private ShortagePredictionProcessRepository repository;

    @Bean
    @Transactional
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
