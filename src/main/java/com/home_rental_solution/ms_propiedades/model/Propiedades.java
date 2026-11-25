package com.home_rental_solution.ms_propiedades.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "propiedades")
public class Propiedades {

    public enum TipoPropiedad{
        departamento,
        casa,
        cabana,
        loft,
        parcela
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPropiedad;

    @Column(
            nullable = false,
            length = 200
    )
    private String titulo;

    @Column(
            nullable = false,
            length = 500
            )
    private String descripcion;

    @Column(
            nullable = false,
            length = 150
    )
    private String ubicacion;

    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal precio;

    @Column(
            name = "id_anfitrion",
            nullable = false
    )
    private Long idAnfitrion;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 50
    )
    private TipoPropiedad tipo;

    @Column(nullable = false)
    private boolean disponible = false;

    private String imagen;

    private Integer capacidad;

    private Integer habitaciones;

    private Integer banos;

    private Integer superficie;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "propiedad_servicios",
            joinColumns = @JoinColumn(name = "id_propiedad")
    )
    @Column(
            name = "servicio",
            length = 100
    )
    private List<String> servicios;

    @Column(nullable = false)
    private boolean destacada = false;
}
