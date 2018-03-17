package pl.com.dddbyexamples.factory.demand.forecasting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DemandForecastingConfiguration {

    @Autowired
    private ProductDemandRepository repository;

    @Bean
    DemandService demandService() {
        return new DemandService(repository);
    }

    @Bean
    ReviewPolicy reviewPolicy() {
        return ReviewPolicy.BASIC;
    }
}
