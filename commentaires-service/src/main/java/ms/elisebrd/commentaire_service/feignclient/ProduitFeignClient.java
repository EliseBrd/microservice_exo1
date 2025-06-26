package ms.elisebrd.commentaire_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* même host/port que ton produit-service */
@FeignClient(name = "produit-service", url = "http://localhost:8081/api/produit")
public interface ProduitFeignClient {
    @GetMapping("/{id}/get-name")
    String getNomById(@PathVariable("id") String id);
}