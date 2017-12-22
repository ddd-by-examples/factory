package pl.com.bottega.factory.demand.forecasting;

public interface DemandEvents {
    void emit(DemandedLevelsChanged event);

    void emit(ReviewRequired event);
}
