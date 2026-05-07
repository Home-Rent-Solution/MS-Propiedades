package com.home_rental_solution.ms_propiedades.repository;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class PropiedadesRepository {

    private List<Propiedades> listaPropiedades = new ArrayList<>();
    private int contadorId = 4;

    public PropiedadesRepository(){
        listaPropiedades.add(new Propiedades(
                1, "Departamento en Santiago Centro",
                "Depto con 2 dormitorios", "Santiago Centro",
                45000000, 1, "departamento" ));

        listaPropiedades.add(new Propiedades(
                2, "Casa en Maipu",
                "Casa con 3 dormitorios y patio", "Maipu",
                78000000, 2, "casa" ));

        listaPropiedades.add(new Propiedades(
                3, "Casa en Providencia",
                "Casa de 5 dormitorios", "Providencia",
                345000000, 1, "casa" ));
    }
    //***CRUD***
    //Obtener todas las propiedades
    public List<Propiedades> obtenerPropiedades(){
        return listaPropiedades;
    }

    //Buscar por ID
    public Propiedades buscarPorId(int id){
        for (Propiedades propiedades : listaPropiedades){
            if (propiedades.getIdPropiedad() == id){
                return propiedades;
            }
        }
        return null;
    }

    //guardar nueva propiedad
    public Propiedades guardar(Propiedades propiedades){
        propiedades.setIdPropiedad(contadorId++);
        listaPropiedades.add(propiedades);
        return propiedades;
    }

    //actualizar propiedad
    public Propiedades actualizar(Propiedades propiedades){
        for (int i = 0; i < listaPropiedades.size(); i++){
            if (listaPropiedades.get(i).getIdPropiedad().equals(propiedades.getIdPropiedad())){
                listaPropiedades.set(i, propiedades);
                return propiedades;
            }
        }
        return null;
    }

    //eliminar por Id
    public void eliminar(int id){
        Propiedades propiedades = buscarPorId(id);
        if (propiedades != null){
            listaPropiedades.remove(propiedades);
        }
    }

    //***EXTRAS***
    //buscar por ID del Anfitrion
    public List<Propiedades> buscarPorAnfitrion(int idAnfitrion){
        List<Propiedades> resultado = new ArrayList<>();
        for (Propiedades propiedades : listaPropiedades){
            if (propiedades.getIdAnfitrion().equals(idAnfitrion)){
                resultado.add(propiedades);
            }
        }
        return resultado;
    }

    //buscar por rango de precio
    public List<Propiedades> buscarPorPrecio(int min, int max){
        List <Propiedades> resultado = new ArrayList<>();
        for (Propiedades propiedades : listaPropiedades){
            if (propiedades.getPrecio() >= min && propiedades.getPrecio() <= max){
                resultado.add(propiedades);
            }
        }
        return resultado;
    }

    //buscar por tipo de propiedad
    public List<Propiedades> buscarPorTipo(String tipo){
        List <Propiedades> resultado = new ArrayList<>();
        for (Propiedades propiedades : listaPropiedades){
            if (propiedades.getTipo().equalsIgnoreCase(tipo)){
                resultado.add(propiedades);
            }
        }
        return resultado;
    }
}
