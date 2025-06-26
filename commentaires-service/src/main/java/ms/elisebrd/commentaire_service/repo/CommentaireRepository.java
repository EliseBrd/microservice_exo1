package ms.elisebrd.commentaire_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ms.elisebrd.commentaire_service.model.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, String> {
    List<Commentaire> findByProduitId(String produitId);

    List<Commentaire> findAllByProduitId(String produitId);
}
