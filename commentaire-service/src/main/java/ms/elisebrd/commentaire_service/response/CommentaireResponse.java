package ms.elisebrd.commentaire_service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ms.elisebrd.commentaire_service.model.Commentaire;

@Getter @Setter
public class CommentaireResponse {
    private String texte;
    private int note;

    public CommentaireResponse(Commentaire commentaire) {
        this.texte = commentaire.getTexte();
        this.note = (commentaire.getNoteQualite() + commentaire.getNoteQualitePrix() + commentaire.getNoteFaciliteUtilisation()) / 3;
    }
}


