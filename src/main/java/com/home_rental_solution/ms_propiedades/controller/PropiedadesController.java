package com.home_rental_solution.ms_propiedades.controller;

import com.home_rental_solution.ms_propiedades.dto.PropiedadesRequestDTO;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesResponseDTO;
import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.service.PropiedadesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/propiedades")
@RequiredArgsConstructor
public class PropiedadesController {

    private final PropiedadesService propiedadesService;

    //***CRUD***
    //GET /propiedades
    @GetMapping
    public ResponseEntity<List<PropiedadesResponseDTO>> getTodas() {
        return ResponseEntity.ok(propiedadesService.mostrarPropiedades());
    }

    //GET /propiedades/id
    @GetMapping("{id}")
    public ResponseEntity<PropiedadesResponseDTO> getPorId(@PathVariable Long id) {
        return ResponseEntity.ok(propiedadesService.mostrarPorId(id));
    }

    //POST /propiedades
    @PostMapping
    public ResponseEntity<PropiedadesResponseDTO> postPropiedad(@Valid @RequestBody PropiedadesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propiedadesService.save(dto));
    }

    //PUT /propiedades/id
    @PutMapping("{id}")
    public ResponseEntity<PropiedadesResponseDTO> putPropiedad(@PathVariable Long id, @Valid @RequestBody PropiedadesRequestDTO dto) {
        return ResponseEntity.ok(propiedadesService.editar(id, dto));
    }

    //DELETE /propiedades/id
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePropiedad(@PathVariable Long id) {
        propiedadesService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    //***EXTRAS***
    //GET /propiedades/id/anfitrion
    @GetMapping("/anfitrion/{idAnfitrion}")
    public ResponseEntity<List<PropiedadesResponseDTO>> getPorAnfitrion(@PathVariable Long idAnfitrion) {
        return ResponseEntity.ok(propiedadesService.mostrarPorAnfitrion(idAnfitrion));
    }

    //GET /propiedades/precio
    @GetMapping("/precio")
    public ResponseEntity<List<PropiedadesResponseDTO>> getPorPrecio(@RequestParam BigDecimal min,
                                                               @RequestParam BigDecimal max) {
        return ResponseEntity.ok(propiedadesService.mostrarPorPrecio(min, max));
    }

    //GET /propiedades/tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PropiedadesResponseDTO>> getPorTipo(@PathVariable Propiedades.TipoPropiedad tipo) {
        return ResponseEntity.ok(propiedadesService.mostrarPorTipo(tipo));
    }

    //GET /propiedades/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<PropiedadesResponseDTO>> getDisponibles() {
        return ResponseEntity.ok(propiedadesService.mostrarDisponibles());
    }

    //GET /propiedades/buscar?ubicacion=algo&precioMin=algo&precioMax=algo
    @GetMapping("/buscar")
    public ResponseEntity<List<PropiedadesResponseDTO>> getUbicacionPrecio(@RequestParam String ubicacion,
                                                                           @RequestParam BigDecimal precioMin,
                                                                           @RequestParam BigDecimal precioMax) {
        return ResponseEntity.ok(propiedadesService.mostrarUbicacionPrecio(ubicacion, precioMin, precioMax));
    }

    //PUT /propiedades/estado--para cambiar el estado
    @PutMapping("{id}/estado")
    public ResponseEntity<PropiedadesResponseDTO> cambiarEstado(@PathVariable Long id) {
        return ResponseEntity.ok(propiedadesService.cambiarEstado(id));
    }
}
