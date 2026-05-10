package com.home_rental_solution.ms_propiedades.repository;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.math.BigDecimal;

@Repository
public interface PropiedadesRepository extends JpaRepository<Propiedades, Integer> {

    //***EXTRAS***
    //Buscar propiedades por Anfitrion
    List<Propiedades> findByIdAnfitrion(Integer idAnfitrion);

    //Buscar propiedades por rango de precio
    List<Propiedades> findByPrecioBetween(BigDecimal min, BigDecimal max);

    //Buscar por tipo de propiedad
    List<Propiedades> findByTipoIgnoreCase (String tipo);

    //Buscar por ubicacion
    List<Propiedades> findByUbicacionContainingIgnoreCase (String ubicacion);

    //Busqueda avanzada: ciudad + rango de precio
    List<Propiedades> findByUbicacionContainingIgnoreCaseAndPrecioBetween(String ubicacion, BigDecimal min, BigDecimal max);

}
