package pl.com.dddbyexamples.factory.demand.forecasting;

public interface ReviewPolicy {

    ReviewPolicy BASIC = (previousDocumented, adjustment, newDocumented) ->
            Adjustment.isStrong(adjustment)
                    && !newDocumented.equals(previousDocumented)
                    && !newDocumented.equals(adjustment.getDemand());

    boolean reviewNeeded(
            Demand previousDocumented,
            Adjustment adjustment,
            Demand newDocumented
    );
}
