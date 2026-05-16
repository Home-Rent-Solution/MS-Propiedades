package com.home_rental_solution.ms_propiedades.model;

import jakarta.persistence.*;
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

    public enum TipoPropiedad{
        casa,
        departamento,
        terreno,
        estudio
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPropiedad;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 150)
    private String ubicacion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "id_anfitrion", nullable = false)
    private Long idAnfitrion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoPropiedad tipo;

    @Column(nullable = false)
    private boolean disponible = false;
}
