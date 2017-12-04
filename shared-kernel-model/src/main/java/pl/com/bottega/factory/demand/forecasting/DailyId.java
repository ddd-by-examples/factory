package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class DailyId {
    private final String refNo;
    private final LocalDate date;
}
