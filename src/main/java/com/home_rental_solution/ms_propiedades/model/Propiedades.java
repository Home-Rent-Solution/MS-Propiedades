package com.home_rental_solution.ms_propiedades.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Propiedades {

    @NotNull(message = "El ID de la propiedad no debe estar vacio")
    private Integer idPropiedad;

    @NotBlank(message = "El titulo de la propiedad es obligatorio")
    private String titulo;

    @NotBlank(message = "La descriocion es obligatoria")
    private String descripcion;

    @NotBlank(message = "La ubicacion es obligatoria")
    private String ubicacion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Integer precio;

    @NotNull(message = "El ID del anfitrion es obligatorio")
    private Integer idAnfitrion;

    @NotBlank(message = "El tipo de propiedad es obligatorio")
    private String tipo;
}
