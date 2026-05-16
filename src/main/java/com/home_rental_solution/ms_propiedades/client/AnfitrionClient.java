package com.home_rental_solution.ms_propiedades.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-anfitriones", url ="${ms.anfitriones.url}")
public interface AnfitrionClient {

    //GET /anfitriones/id/validar
    //devuelve true si el anfitrion esta verificado
    @GetMapping("/api/v1/anfitriones/{id}/validar")
    boolean validarAnfitrion(@PathVariable Long id);
}
