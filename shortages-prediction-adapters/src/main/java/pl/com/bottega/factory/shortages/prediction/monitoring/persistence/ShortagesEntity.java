package pl.com.bottega.factory.shortages.prediction.monitoring.persistence;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.bottega.factory.product.management.RefNoId;
import pl.com.bottega.factory.shortages.prediction.Shortages;
import pl.com.bottega.tools.JsonConverter;
import pl.com.bottega.tools.TechnicalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Shortage")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "refNo")
public class ShortagesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Column
    private String refNo;
    @Column(length = 1024)
    @Convert(converter = ShortagesAsJson.class)
    @Setter
    private Shortages shortages;

    public ShortagesEntity(String refNo) {
        this.refNo = refNo;
    }

    public RefNoId createId() {
        return new ShortagesEntityId(refNo, id);
    }

    public static RefNoId createId(RefNoId id) {
        return id instanceof ShortagesEntityId ? id : new ShortagesEntityId(id.getRefNo());
    }

    public static class ShortagesAsJson extends JsonConverter<Shortages> {
        public ShortagesAsJson() {
            super(Shortages.class);
        }
    }

    @Getter
    static class ShortagesEntityId extends RefNoId implements TechnicalId {

        Long id;

        ShortagesEntityId(String refNo) {
            super(refNo);
        }

        ShortagesEntityId(String refNo, long id) {
            super(refNo);
            this.id = id;
        }
    }
}
