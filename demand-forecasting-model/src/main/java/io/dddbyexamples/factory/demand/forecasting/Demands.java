package io.dddbyexamples.factory.demand.forecasting;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class Demands implements ProductDemand.Demands {
    final Map<LocalDate, DailyDemand> fetched = new HashMap<>();
    Function<LocalDate, DailyDemand> fetch;

    @Override
    public DailyDemand get(LocalDate date) {
        return fetched.computeIfAbsent(date, fetch);
    }
}
