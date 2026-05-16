package com.home_rental_solution.ms_propiedades.dto;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadesResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private BigDecimal precio;
    private Long idAnfitrion;
    private Propiedades.TipoPropiedad tipo;
    private boolean disponible;
}
