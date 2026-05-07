package com.home_rental_solution.ms_propiedades.controller;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.service.PropiedadesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/propiedades")
public class PropiedadesController {

    @Autowired
    private PropiedadesService propiedadesService;

    //***CRUD***
    //GET /propiedades
    @GetMapping
    public ResponseEntity<List<Propiedades>> getTodas(){
        return ResponseEntity.ok(propiedadesService.getPropiedades());
    }

    //GET /propiedades/id
    @GetMapping("{id}")
    public ResponseEntity<?> getPropiedadId(@PathVariable int id){
        Propiedades propiedades = propiedadesService.getPorId(id);
        if (propiedades == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La propiedad con ID " + id + " no existe");
        }
        return ResponseEntity.ok(propiedades);
    }

    //POST /propiedades
    @PostMapping
    public ResponseEntity<?> postPropiedad(@Valid @RequestBody Propiedades propiedadNueva){
        try{
            Propiedades propiedadGuardada = propiedadesService.save(propiedadNueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(propiedadGuardada);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT /propiedades/id
    @PutMapping("{id}")
    public ResponseEntity<?> putPropiedad(@PathVariable int id, @Valid @RequestBody Propiedades propiedadActualizada){
        try{
            Propiedades propiedades = propiedadesService.editar(id, propiedadActualizada);
            return ResponseEntity.ok(propiedades);
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
    public ResponseEntity<List<Propiedades>> getAnfitrion(@PathVariable int idAnfitrion){
        return ResponseEntity.ok(propiedadesService.getPorAnfitrion(idAnfitrion));
    }

    //GET /propiedades/precio
    @GetMapping("/precio")
    public ResponseEntity<?> getPrecio(@RequestParam int min, @RequestParam int max){
        try{
            return ResponseEntity.ok(propiedadesService.getPorPrecio(min, max));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //GET /propiedades/tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Propiedades>> getTipo(@PathVariable String tipo){
        return ResponseEntity.ok(propiedadesService.getPorTipo(tipo));
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
