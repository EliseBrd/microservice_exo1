package fr.formation.feignclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.formation.response.CommentaireResponse;

@FeignClient(name = "commentaire-service", url = "http://localhost:8082/api/commentaire", fallback = CommentaireFeignClient.Fallback.class)
public interface CommentaireFeignClient {
    @GetMapping("/by-produit-id/{produitId}")
    public List<CommentaireResponse> findAllByProduitId(@PathVariable("produitId") String produitId);

    @GetMapping("/note/by-produit-id/{produitId}")
    public int getNoteByProduitId(@PathVariable("produitId") String produitId);

    @Component
    public static class Fallback implements CommentaireFeignClient {
        @Override
        public List<CommentaireResponse> findAllByProduitId(String produitId) {
            return new ArrayList<>();
        }

        @Override
        public int getNoteByProduitId(String produitId) {
            return -2;
        }
    }

}
