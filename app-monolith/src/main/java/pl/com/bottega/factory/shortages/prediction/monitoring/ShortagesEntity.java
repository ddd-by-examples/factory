package pl.com.bottega.factory.shortages.prediction.monitoring;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.com.bottega.factory.shortages.prediction.Shortages;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Shortage")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "refNo")
class ShortagesEntity {

    @Id
    @Column
    private String refNo;

    @Column
    @Convert(converter = ShortagesAsJson.class)
    Shortages shortages;

    ShortagesEntity(String refNo) {
        this.refNo = refNo;
    }

    public static class ShortagesAsJson extends JsonConverter<Shortages> {
        public ShortagesAsJson() {
            super(Shortages.class);
        }
    }
}
