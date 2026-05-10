package com.home_rental_solution.ms_propiedades.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "propiedades")
public class Propiedades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPropiedad;

    @NotBlank(message = "El titulo de la propiedad es obligatorio")
    @Size(max = 200, message = "El titulo no puede superar los 200 caracteres")
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "La descriocion es obligatoria")
    @Size(max = 500, message = "La descripcion no puede superar los 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotBlank(message = "La ubicacion es obligatoria")
    @Size(max = 150, message = "La ubicacion no puede superar los 150 caracteres")
    @Column(nullable = false, length = 150)
    private String ubicacion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    private BigDecimal precio;

    @NotNull(message = "El ID del anfitrion es obligatorio")
    @Column(nullable = false)
    private Integer idAnfitrion;

    @NotBlank(message = "El tipo de propiedad es obligatorio")
    @Size(max = 50, message = "El tipo no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String tipo;
}
