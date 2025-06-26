package fr.formation.api;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fr.formation.exception.ProduitNotFoundException;
import fr.formation.feignclient.CommentaireFeignClient;
import fr.formation.model.Produit;
import fr.formation.repo.ProduitRepository;
import fr.formation.request.CreateOrUpdateProduitRequest;
import fr.formation.response.CommentaireResponse;
import fr.formation.response.ProduitByIdResponse;
import fr.formation.response.ProduitResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produit")
@RequiredArgsConstructor
public class ProduitApiController {
    private final ProduitRepository repository;
    private final CommentaireFeignClient commentaireFeignClient;

    @GetMapping
    public List<ProduitResponse> findAll() {
        return this.repository.findAll()
            .stream()
            .map(p -> {
                int note = this.commentaireFeignClient.getNoteByProduitId(p.getId());

                return ProduitResponse.builder()
                    .id(p.getId())
                    .nom(p.getNom())
                    .prix(p.getPrix())
                    .note(note)
                    .build()
                ;
            })
            .toList()
        ;
    }

    @GetMapping("/{id}")
    public ProduitByIdResponse findById(@PathVariable String id) {
        Produit produit = this.repository.findById(id).orElseThrow(ProduitNotFoundException::new);
        List<CommentaireResponse> commentaires = this.commentaireFeignClient.findAllByProduitId(id);
        ProduitByIdResponse resp = new ProduitByIdResponse();

        int note = (int)commentaires
            .stream()
            .mapToInt(CommentaireResponse::getNote)
            .average()
            .orElse(-1);

        BeanUtils.copyProperties(produit, resp);

        resp.setCommentaires(commentaires
            .stream()
            .map(c -> CommentaireResponse.builder()
                .texte(c.getTexte())
                .note(c.getNote())
                .build()
            )
            .toList()
        );

        resp.setNote(note);

        return resp;
    }

    @GetMapping("/{id}/get-name")
    public String getNameById(@PathVariable String id) {
        return this.repository.findById(id).orElseThrow(ProduitNotFoundException::new).getNom();
    }
    
    @GetMapping("/{id}/is-notable")
    public boolean isNotableById(@PathVariable String id) {
        return this.repository.findById(id).orElseThrow(ProduitNotFoundException::new).isNotable();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateOrUpdateProduitRequest request) {
        Produit produit = new Produit();

        BeanUtils.copyProperties(request, produit);

        this.repository.save(produit);

        return produit.getId();
    }

    // Modification d'un produit
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody CreateOrUpdateProduitRequest request) {
        Produit produit = this.repository.findById(id)
                .orElseThrow(ProduitNotFoundException::new);

        BeanUtils.copyProperties(request, produit);

        this.repository.save(produit);
    }

    // Suppression d'un produit
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new ProduitNotFoundException();
        }
        repository.deleteById(id);
    }
}
