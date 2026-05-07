package com.home_rental_solution.ms_propiedades.service;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropiedadesService {

    @Autowired
    private PropiedadesRepository propiedadesRepository;

    //***CRUD***
    //GET /propiedades
    public List<Propiedades> getPropiedades(){
        return propiedadesRepository.obtenerPropiedades();
    }

    //GET /propiedades/id
    public Propiedades getPorId(int id){
        return propiedadesRepository.buscarPorId(id);
    }

    //POST /propiedades
    public Propiedades save (Propiedades nuevaPropiedad) throws Exception{
        if (nuevaPropiedad.getPrecio() <= 0){
            throw new Exception("El precio debe ser mayor a 0");
        }
        return propiedadesRepository.guardar(nuevaPropiedad);
    }

    //PUT /propiedades/id
    public Propiedades editar(int id, Propiedades propiedadEditada) throws Exception{
        Propiedades propiedadExistente = propiedadesRepository.buscarPorId(id);
        if (propiedadExistente == null){
            throw new Exception("La propiedad con ID " + id + " no existe");
        }
        if (propiedadEditada.getPrecio() <= 0){
            throw  new Exception("El precio debe ser mayor a 0");
        }
        propiedadEditada.setIdPropiedad(id);
        return propiedadesRepository.actualizar(propiedadEditada);
    }

    // DELETE /propiedad/id
    public void borrar (int id) throws Exception{
        if (propiedadesRepository.buscarPorId(id) == null){
            throw new Exception("La propiedad con ID " + id + " no existe");
        }
        propiedadesRepository.eliminar(id);
    }

    //***EXTRAS***
    //GET /propiedades/usuario/id
    public List<Propiedades> getPorAnfitrion(int idAnfitrion){
        return propiedadesRepository.buscarPorAnfitrion(idAnfitrion);
    }

    //GET /propiedades/precio
    public List<Propiedades> getPorPrecio(int min, int max) throws Exception{
        if (min < 0 || max < 0){
            throw new Exception("Los valores de precio no pueden ser negativos");
        }
        if (min > max){
            throw  new Exception("El precio minimo no puede ser mayor que el precio maximo");
        }
        return propiedadesRepository.buscarPorPrecio(min, max);
    }

    //GET /propiedades/tipo
    public List<Propiedades> getPorTipo(String tipo){
        return propiedadesRepository.buscarPorTipo(tipo);
    }
}
