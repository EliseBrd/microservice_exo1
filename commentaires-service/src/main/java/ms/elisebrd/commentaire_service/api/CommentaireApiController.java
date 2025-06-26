package ms.elisebrd.commentaire_service.api;

import ms.elisebrd.commentaire_service.repo.CommentaireRepository;
import ms.elisebrd.commentaire_service.request.UpdateCommentaireRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ms.elisebrd.commentaire_service.request.CreateCommentaireRequest;
import ms.elisebrd.commentaire_service.response.CommentaireByIdResponse;
import ms.elisebrd.commentaire_service.response.CommentaireResponse;
import ms.elisebrd.commentaire_service.service.CommentaireService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/commentaire")
@RequiredArgsConstructor
public class CommentaireApiController {

    private final CommentaireService service;
    private final CommentaireRepository commentaireRepository;

    /* ---------- GET ---------- */

    @GetMapping("/{id}")
    public CommentaireByIdResponse one(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/by-produit-id/{produitId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentaireResponse> findByProductId(@PathVariable String produitId) {
        var commentaires = commentaireRepository.findAllByProduitId(produitId);
        return commentaires.stream()
                .map(CommentaireResponse::new)
                .toList();
    }

    @GetMapping("/note/by-produit-id/{produitId}")
    public int note(@PathVariable String produitId) {
        return service.noteGlobaleProduit(produitId);
    }

    /* ---------- POST ---------- */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateCommentaireRequest req) {
        return service.create(req);
    }

    /* ---------- PUT ---------- */

    @PutMapping("/{id}")
    public void update(@PathVariable String id,
                       @RequestBody UpdateCommentaireRequest req) {  // <-- changer ici
        service.update(id, req);
    }

    /* ---------- DELETE ---------- */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}