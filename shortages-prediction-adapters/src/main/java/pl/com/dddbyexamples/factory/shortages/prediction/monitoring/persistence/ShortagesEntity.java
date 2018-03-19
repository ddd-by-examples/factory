package pl.com.dddbyexamples.factory.shortages.prediction.monitoring.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.dddbyexamples.factory.product.management.RefNoId;
import pl.com.dddbyexamples.factory.shortages.prediction.Shortage;
import pl.com.dddbyexamples.tools.JsonConverter;
import pl.com.dddbyexamples.tools.TechnicalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Shortage")
@Table(schema = "shortages_prediction")
@Getter
@NoArgsConstructor
public class ShortagesEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;
    private String refNo;
    @Setter
    @Convert(converter = ShortageAsJson.class)
    private Shortage shortage;

    public ShortagesEntity(String refNo) {
        this.refNo = refNo;
    }

    public RefNoId createId() {
        return new ShortagesEntityId(refNo, id);
    }

    public static RefNoId createId(RefNoId id) {
        return id instanceof ShortagesEntityId ? id : new ShortagesEntityId(id.getRefNo());
    }

    public static class ShortageAsJson extends JsonConverter<Shortage> {
        public ShortageAsJson() {
            super(Shortage.class);
        }
    }

    @Getter
    static class ShortagesEntityId extends RefNoId implements TechnicalId {

        private Long id;

        ShortagesEntityId(String refNo) {
            super(refNo);
        }

        ShortagesEntityId(String refNo, long id) {
            super(refNo);
            this.id = id;
        }
    }
}
