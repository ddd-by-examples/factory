package pl.com.bottega.factory.demand.forecasting;

interface ProductDemandRepository {
    ProductDemand get(String refNo);

    void save(ProductDemand model);

    void initDemandsFor(String refNo);
}
