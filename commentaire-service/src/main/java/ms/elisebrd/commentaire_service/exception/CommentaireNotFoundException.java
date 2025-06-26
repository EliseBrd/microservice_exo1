package ms.elisebrd.commentaire_service.exception;

public class CommentaireNotFoundException extends RuntimeException {
  public CommentaireNotFoundException(String id) {
    super("Commentaire " + id + " introuvable");
  }
}