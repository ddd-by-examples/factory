package io.dddbyexamples.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DailyId {
    private final String refNo;
    private final LocalDate date;
}
