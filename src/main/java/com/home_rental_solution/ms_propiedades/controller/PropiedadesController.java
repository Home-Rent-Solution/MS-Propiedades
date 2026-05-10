package com.home_rental_solution.ms_propiedades.controller;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.service.PropiedadesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/propiedades")
@RequiredArgsConstructor
public class PropiedadesController {

    private final PropiedadesService propiedadesService;

    //***CRUD***
    //GET /propiedades
    @GetMapping
    public ResponseEntity<List<Propiedades>> getTodas(){
        return ResponseEntity.ok(propiedadesService.mostrarPropiedades());
    }

    //GET /propiedades/id
    @GetMapping("{id}")
    public ResponseEntity<?> getPorId(@PathVariable int id){
        Propiedades propiedades = propiedadesService.mostrarPorId(id);
        if (propiedades == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La propiedad con ID: " + id + " no existe");
        }
        return ResponseEntity.ok(propiedades);
    }

    //POST /propiedades
    @PostMapping
    public ResponseEntity<?> postPropiedad(@Valid @RequestBody Propiedades nuevaPropiedad){
        try{
            Propiedades propiedadGuardada = propiedadesService.save(nuevaPropiedad);
            return ResponseEntity.status(HttpStatus.CREATED).body(propiedadGuardada);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT /propiedades/id
    @PutMapping("{id}")
    public ResponseEntity<?> putPropiedad(@PathVariable int id, @Valid @RequestBody Propiedades propiedadEditada){
        try{
            Propiedades propiedadActualizada = propiedadesService.editar(id, propiedadEditada);
            return ResponseEntity.ok(propiedadActualizada);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //DELETE /propiedades/id
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePropiedad(@PathVariable int id){
        try{
            propiedadesService.borrar(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //***EXTRAS***
    //GET /propiedaes/id/anfitrion
    @GetMapping("/anfitrion/{idAnfitrion}")
    public ResponseEntity<List<Propiedades>> getPorAnfitrion(@PathVariable int idAnfitrion){
        return ResponseEntity.ok(propiedadesService.mostrarPorAnfitrion(idAnfitrion));
    }

    //GET /propiedades/precio
    @GetMapping("/precio")
    public ResponseEntity<?> getPorPrecio(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        try{
            return ResponseEntity.ok(propiedadesService.mostrarPorPrecio(min, max));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //GET /propiedades/tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Propiedades>> getPorTipo(@PathVariable String tipo){
        return ResponseEntity.ok(propiedadesService.mostrarPorTipo(tipo));
    }

    //GET /propiedades/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Propiedades>> getDisponibles(){
        return ResponseEntity.ok(propiedadesService.mostrarDisponibles());
    }

    //GET /propiedades/buscar?ubicacion=algo&precioMin=algo&precioMax=algo
    @GetMapping("/buscar")
    public ResponseEntity<?> getUbicacionPrecio(@RequestParam String ubicacion,
                                                @RequestParam BigDecimal precioMin,
                                                @RequestParam BigDecimal precioMax){
        try{
            return ResponseEntity.ok(propiedadesService.mostrarUbicacionPrecio(ubicacion, precioMin, precioMax));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Manejo de errores de validacion
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> manejarErroresValidacion(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return errores;
    }
}
