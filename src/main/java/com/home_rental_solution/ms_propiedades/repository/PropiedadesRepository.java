package com.home_rental_solution.ms_propiedades.repository;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.model.Propiedades.TipoPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.math.BigDecimal;

@Repository
public interface PropiedadesRepository extends JpaRepository<Propiedades, Integer> {

    //***EXTRAS***
    //Buscar propiedades por Anfitrion*
    List<Propiedades> findByIdAnfitrion(Integer idAnfitrion);

    //Buscar propiedades por rango de precio
    @Query("SELECT p FROM Propiedades p WHERE p.precio BETWEEN :min AND :max ORDER BY p.precio")
    List<Propiedades> findByPrecioBetween(@Param ("min") BigDecimal min, @Param ("max") BigDecimal max);

    //Buscar por tipo de propiedad*
    List<Propiedades> findByTipo (TipoPropiedad tipo);

    //Buscar por ubicacion
    @Query("SELECT p FROM Propiedades p WHERE LOWER(p.ubicacion) LIKE LOWER(CONCAT('%', :ubicacion, '%'))")
    List<Propiedades> findByUbicacionContainingIgnoreCase (@Param("ubicacion") String ubicacion);

    //Busqueda avanzada: ciudad + rango de precio
    @Query("SELECT p FROM Propiedades p WHERE LOWER(p.ubicacion) LIKE LOWER (CONCAT('%', :ubicacion, '%'))" + "AND " +
            "p.precio BETWEEN :min AND :max ORDER BY p.precio")
    List<Propiedades> findByUbicacionContainingIgnoreCaseAndPrecioBetween(@Param ("ubicacion") String ubicacion,
                                                                          @Param ("min") BigDecimal min,
                                                                          @Param ("max") BigDecimal max);
    //Busqueda por disponibilidad*
    List<Propiedades> findByDisponibleTrue();
}
