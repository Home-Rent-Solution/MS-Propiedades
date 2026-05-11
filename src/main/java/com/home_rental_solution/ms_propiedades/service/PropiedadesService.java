package com.home_rental_solution.ms_propiedades.service;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PropiedadesService {

    private final PropiedadesRepository propiedadesRepository;

    //***CRUD**
    //GET /propiedades
    public List<Propiedades> mostrarPropiedades(){
        return propiedadesRepository.findAll();
    }

    //GET /propiedades/id
    public Propiedades mostrarPorId(int id){
        return propiedadesRepository.findById(id).orElse(null);
    }

    //POST /propiedades
    public Propiedades save (Propiedades nuevaPropiedad) throws Exception{
        if (nuevaPropiedad.getPrecio() == null || nuevaPropiedad.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("El precio debe ser mayor a 0");
        }
        return propiedadesRepository.save(nuevaPropiedad);
    }

    //PUT /propiedades/Id
    public Propiedades editar(int id, Propiedades propiedadEditada) throws Exception{
        Propiedades propiedadExistente = propiedadesRepository.findById(id).orElse(null);
        if (propiedadExistente == null){
            throw new Exception("La propiedad con ID: " + id + " no existe");
        }
        if (propiedadEditada.getPrecio() == null || propiedadEditada.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw  new Exception("El precio debe ser mayor a 0");
        }
        propiedadEditada.setIdPropiedad(id);
        propiedadEditada.setDisponible(propiedadExistente.isDisponible());
        return propiedadesRepository.save(propiedadEditada);
    }

    // DELETE /propiedad/id
    public void borrar (int id) throws Exception{
        if (!propiedadesRepository.existsById(id)){
            throw new Exception("La propiedad con ID: " + id + " no existe");
        }
        propiedadesRepository.deleteById(id);
    }

    //***EXTRAS***
    //GET /propiedades/usuario/id
    public List<Propiedades> mostrarPorAnfitrion(int idAnfitrion){
        return propiedadesRepository.findByIdAnfitrion(idAnfitrion);
    }

    //GET /propiedades/precio
    public List<Propiedades> mostrarPorPrecio(BigDecimal min, BigDecimal max) throws Exception{
        if (min == null || max == null){
            throw new Exception("Debe ingresar ambos valores de precio");
        }
        if (min.compareTo(max) > 0){
            throw  new Exception("El precio minimo no puede ser mayor que el precio maximo");
        }
        return propiedadesRepository.findByPrecioBetween(min, max);
    }

    //GET /propiedades/tipo
    public List<Propiedades> mostrarPorTipo(Propiedades.TipoPropiedad tipo){
        return propiedadesRepository.findByTipo(tipo);
    }

    //GET /propiedades/buscar?ubicacion=&precioMin=&precioMax=
    public List<Propiedades> mostrarUbicacionPrecio(String ubicacion, BigDecimal precioMin, BigDecimal precioMax) throws Exception{
        if (precioMin == null || precioMax == null){
            throw new Exception("Debe ingresar ambos valores de precio");
        }
        if (precioMin.compareTo(precioMax) > 0){
            throw new Exception("El precio minimo no puede ser mayor que el precio maximo");
        }
        return propiedadesRepository.findByUbicacionContainingIgnoreCaseAndPrecioBetween(ubicacion, precioMin, precioMax);
    }

    //GET /propiedades/disponibles
    public List<Propiedades> mostrarDisponibles(){
        return propiedadesRepository.findByDisponibleTrue();
    }

    //PUT /propiedades/estado
    public Propiedades cambiarEstado(int id) throws Exception{
        Propiedades propiedad = propiedadesRepository.findById(id).orElse(null);
        if (propiedad ==  null){
            throw new Exception("La propiedad con ID: " + id + " no existe");
        }
        propiedad.setDisponible(!propiedad.isDisponible());
        return propiedadesRepository.save(propiedad);
    }
}
