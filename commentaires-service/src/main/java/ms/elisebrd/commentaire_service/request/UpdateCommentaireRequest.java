package ms.elisebrd.commentaire_service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentaireRequest {
    private String texte;
    private int noteQualite;
    private int noteQualitePrix;
    private int noteFaciliteUtilisation;
}