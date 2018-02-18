package pl.com.bottega.factory.demand.forecasting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;

@Configuration
class DemandForecastingConfiguration {

    @Autowired
    private ProductDemandRepository repository;

    @Bean
    @Transactional
    DemandService demandService() {
        return new DemandService(repository);
    }

    @Bean
    ReviewPolicy reviewPolicy() {
        return ReviewPolicy.BASIC;
    }
}
