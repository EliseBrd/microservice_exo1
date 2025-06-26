package ms.elisebrd.commentaire_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentaireByIdResponse {
    private String texte;
    private int note;
}
