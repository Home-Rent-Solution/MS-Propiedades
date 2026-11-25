package com.home_rental_solution.ms_propiedades.dto;

import com.home_rental_solution.ms_propiedades.model.Propiedades.TipoPropiedad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadesRequestDTO {

    //id no se incluye porque MySQL lo genera

    //disponible no se incluye porque se maneja con PUT

    @NotBlank(message = "El nombre es obligatorio")
    @Size(
            max = 150,
            message = "El nombre no puede superar los 150 caracteres"
    )
    private String titulo;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(
            max = 500,
            message = "La descripcion no puede superar los 500 caracteres"
    )
    private String descripcion;

    @NotBlank(message = "La ubicacion es obligatoria")
    @Size(
            max = 200,
            message = "La ubicacion no puede superar los 200 caracteres"
    )
    private String ubicacion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El id del anfitrion es obligatorio")
    private Long idAnfitrion;

    @NotNull(message = "El tipo de propiedad es obligatorio")
    private TipoPropiedad tipo;

    //campos nuevos: opcionales, pero si vienen deben ser validos
    @Size(
            max = 255,
            message = "La ruta de la imagen no puede superar los 255 caracteres"
    )
    private String imagen;

    @Positive(message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @Positive(message = "Las habitaciones deben ser mayor a 0")
    private Integer habitaciones;

    @Positive(message = "Los baños deben ser mayor a 0")
    private Integer banos;

    @Positive(message = "La superficie debe ser mayor a 0")
    private Integer superficie;

    private List<String> servicios;

    private Boolean destacada;
}
