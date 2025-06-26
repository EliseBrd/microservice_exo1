package ms.elisebrd.commentaire_service.service;

import java.util.List;

import ms.elisebrd.commentaire_service.request.UpdateCommentaireRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import ms.elisebrd.commentaire_service.exception.CommentaireNotFoundException;
import ms.elisebrd.commentaire_service.feignclient.ProduitFeignClient;
import ms.elisebrd.commentaire_service.model.Commentaire;
import ms.elisebrd.commentaire_service.repo.CommentaireRepository;
import ms.elisebrd.commentaire_service.request.CreateCommentaireRequest;
import ms.elisebrd.commentaire_service.response.CommentaireByIdResponse;
import ms.elisebrd.commentaire_service.response.CommentaireResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentaireService {
    private final CommentaireRepository repo;
    private final ProduitFeignClient produitFeignClient;

    /* --- CRUD + méthodes métier --- */

    public CommentaireByIdResponse findById(String id) {
        Commentaire c = repo.findById(id)
                .orElseThrow(() -> new CommentaireNotFoundException(id));

        CommentaireByIdResponse resp = new CommentaireByIdResponse();
        resp.setTexte(c.getTexte());
        int note = Math.round(
                (c.getNoteQualite() + c.getNoteQualitePrix() + c.getNoteFaciliteUtilisation()) / 3.0f
        );
        resp.setNote(note);
        return resp;
    }

    public List<CommentaireResponse> findByProduitId(String produitId) {
        return repo.findByProduitId(produitId).stream()
                .map(CommentaireResponse::new)
                .toList();
    }

    public int noteGlobaleProduit(String produitId) {
        return (int) repo.findByProduitId(produitId).stream()
                .mapToInt(c -> Math.round(
                        (c.getNoteQualite() + c.getNoteQualitePrix() + c.getNoteFaciliteUtilisation()) / 3.0f))
                .average()
                .orElse(0);
    }

    public String create(CreateCommentaireRequest req) {
        Commentaire c = new Commentaire();
        BeanUtils.copyProperties(req, c);
        repo.save(c);
        return c.getId();
    }

    public void update(String id, UpdateCommentaireRequest req) {
        Commentaire c = repo.findById(id)
                .orElseThrow(() -> new CommentaireNotFoundException(id));
        BeanUtils.copyProperties(req, c);
        repo.save(c);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
