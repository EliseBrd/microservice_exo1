package ms.elisebrd.commentaire_service.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Commentaire {
    @Id
    @UuidGenerator
    private String id;

    private String produitId;

    private String texte;

    private int noteQualite;        // 0-5
    private int noteQualitePrix;    // 0-5
    private int noteFaciliteUtilisation;   // 0-5
}