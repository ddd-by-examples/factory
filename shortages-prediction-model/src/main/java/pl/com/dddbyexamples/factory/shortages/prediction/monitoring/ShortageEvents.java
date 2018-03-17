package pl.com.dddbyexamples.factory.shortages.prediction.monitoring;

public interface ShortageEvents {
    void emit(NewShortage event);

    void emit(ShortageSolved event);
}
