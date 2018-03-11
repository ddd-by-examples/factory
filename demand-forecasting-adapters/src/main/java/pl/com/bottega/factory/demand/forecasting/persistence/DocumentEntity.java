package pl.com.bottega.factory.demand.forecasting.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import pl.com.bottega.factory.demand.forecasting.Document;
import pl.com.bottega.tools.JsonConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Entity(name = "Document")
@Table(schema = "demand_forecasting")
@Getter
@NoArgsConstructor
public class DocumentEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String refNo;
    @LastModifiedDate
    private Instant saved;
    private String originalUri;
    private String storedUri;
    @Setter
    @Convert(converter = DocumentAsJson.class)
    private Document document;

    @Setter
    private LocalDate cleanAfter;

    public DocumentEntity(String originalUri, String storedUri, Document document) {
        saved = Instant.now();
        this.originalUri = originalUri;
        this.storedUri = storedUri;
        this.document = document;
        this.refNo = document.getRefNo();
    }

    public static class DocumentAsJson extends JsonConverter<Document> {
        public DocumentAsJson() {
            super(Document.class);
        }
    }
}
