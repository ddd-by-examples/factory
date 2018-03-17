package pl.com.dddbyexamples.factory.demand.forecasting;

interface ProductDemandRepository {
    ProductDemand get(String refNo);

    void save(ProductDemand model);

    void initNewProduct(String refNo);
}
