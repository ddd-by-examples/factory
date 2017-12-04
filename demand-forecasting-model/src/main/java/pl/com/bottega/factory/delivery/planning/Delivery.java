package pl.com.bottega.factory.delivery.planning;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Delivery {
    String refNo;
    LocalDateTime time;
    long level;
}
