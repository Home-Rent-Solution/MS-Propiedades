package com.home_rental_solution.ms_propiedades.config;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.model.Propiedades.TipoPropiedad;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final PropiedadesRepository propiedadesRepository;


    @Autowired
    public DataInitializer(PropiedadesRepository propiedadesRepository) {
        this.propiedadesRepository = propiedadesRepository;
    }

    @Override
    public void run(String... args){
        //evita insertar datos duplicados en cada arranque
        if (propiedadesRepository.count() > 0){
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial");
            return;
        }
        log.info(">>> DataInitializer: BD vacia detectada, insertando las propiedades del frontend");
        //mismas 20 propiedades del frontend (propiedades.json), en el mismo orden: quedan con id 1 a 20
        //orden de los datos: titulo, tipo, ubicacion, precio por noche, id anfitrion,
        //descripcion, imagen, capacidad, habitaciones, banos, superficie (m2), destacada, servicios
        propiedadesRepository.saveAll(List.of(
                //1
                propiedad("Departamento con vista al mar", TipoPropiedad.departamento, "Viña del Mar, Valparaíso", 68000, 1L,
                        "Departamento luminoso a tres cuadras de la playa, con balcón orientado al poniente y estacionamiento incluido. El edificio cuenta con conserjería las 24 horas y piscina en el último piso.",
                        "/img/img-tarjeta-1.avif", 4, 2, 1, 68, true,
                        List.of("WiFi de fibra óptica", "Estacionamiento privado", "Cocina equipada", "Acceso a piscina")),
                //2
                propiedad("Casa familiar en barrio residencial", TipoPropiedad.casa, "Ñuñoa, Santiago", 92000, 2L,
                        "Casa de dos pisos con antejardín y patio trasero, en una calle tranquila cerca del metro. Ideal para familias que visitan Santiago.",
                        "/img/img-tarjeta-2.avif", 6, 3, 2, 120, true,
                        List.of("WiFi", "Estacionamiento", "Cocina equipada", "Lavadora", "Patio con quincho")),
                //3
                propiedad("Cabaña de montaña con tinaja", TipoPropiedad.cabana, "Pucón, La Araucanía", 75000, 1L,
                        "Cabaña de madera rodeada de bosque nativo, con tinaja de agua caliente y vista al volcán Villarrica. A diez minutos del centro de Pucón.",
                        "/img/img-tarjeta-3.avif", 4, 2, 1, 55, true,
                        List.of("Tinaja", "Estufa a leña", "Estacionamiento", "Cocina equipada")),
                //4
                propiedad("Loft céntrico para profesionales", TipoPropiedad.loft, "Providencia, Santiago", 54000, 2L,
                        "Loft de diseño con escritorio de trabajo e internet de alta velocidad, pensado para estadías laborales. A pasos de oficinas, cafés y restaurantes.",
                        "/img/img-tarjeta-4.avif", 2, 1, 1, 40, true,
                        List.of("WiFi de fibra óptica", "Escritorio de trabajo", "Gimnasio en el edificio")),
                //5
                propiedad("Casa de playa con acceso privado", TipoPropiedad.casa, "Algarrobo, Valparaíso", 110000, 1L,
                        "Casa amplia con ventanal hacia el mar y bajada privada a la playa. Terraza con parrilla y espacio para grupos grandes.",
                        "/img/img-tarjeta-5.avif", 8, 4, 3, 180, true,
                        List.of("Acceso privado a la playa", "Terraza con parrilla", "WiFi", "Estacionamiento")),
                //6
                propiedad("Cabaña frente al lago", TipoPropiedad.cabana, "Puerto Varas, Los Lagos", 79000, 2L,
                        "Cabaña a orillas del lago Llanquihue con muelle propio y kayaks disponibles. Vista directa a los volcanes Osorno y Calbuco.",
                        "/img/img-tarjeta-6.avif", 5, 2, 1, 70, true,
                        List.of("Muelle privado", "Kayaks", "Estufa a leña", "WiFi")),
                //7
                propiedad("Parcela con casa de adobe restaurada", TipoPropiedad.parcela, "Santa Cruz, O'Higgins", 86000, 1L,
                        "Casa de adobe restaurada en una parcela rodeada de viñedos, con piscina y huerto. Cerca de las principales viñas del valle de Colchagua.",
                        "/img/img-tarjeta-7.avif", 6, 3, 2, 140, false,
                        List.of("Piscina", "Quincho", "Estacionamiento", "Huerto")),
                //8
                propiedad("Departamento amoblado en el centro", TipoPropiedad.departamento, "Concepción, Biobío", 48000, 2L,
                        "Departamento amoblado en pleno centro, cerca de universidades y del mall. Buena opción para estadías cortas de trabajo o estudio.",
                        "/img/img-tarjeta-8.avif", 3, 1, 1, 45, false,
                        List.of("WiFi", "Cocina equipada", "Conserjería")),
                //9
                propiedad("Casa con patio en barrio tradicional", TipoPropiedad.casa, "La Serena, Coquimbo", 64000, 1L,
                        "Casa con cocina equipada, comedor integrado y patio interior, en un barrio tradicional a quince minutos de la playa.",
                        "/img/img-tarjeta-9.avif", 5, 3, 1, 95, false,
                        List.of("WiFi", "Patio interior", "Cocina equipada", "Estacionamiento")),
                //10
                propiedad("Departamento frente a la playa Cavancha", TipoPropiedad.departamento, "Iquique, Tarapacá", 72000, 2L,
                        "Departamento en primera línea frente a la playa Cavancha, con balcón y vista al océano. A pasos de la costanera, restaurantes y zonas de surf.",
                        "/img/img-tarjeta-1.avif", 4, 2, 2, 75, false,
                        List.of("WiFi", "Estacionamiento", "Piscina", "Cocina equipada")),
                //11
                propiedad("Cabaña junto al río", TipoPropiedad.cabana, "Panguipulli, Los Ríos", 70000, 1L,
                        "Cabaña de madera a orillas del río, rodeada de bosque nativo. Ideal para desconectarse, pescar y recorrer los lagos de la zona.",
                        "/img/img-tarjeta-3.avif", 4, 2, 1, 50, false,
                        List.of("Estufa a leña", "Quincho", "Estacionamiento", "Ropa de cama")),
                //12
                propiedad("Casa en el valle del Elqui", TipoPropiedad.casa, "Vicuña, Coquimbo", 83000, 2L,
                        "Casa rodeada de cerros y viñedos pisqueros, con terraza para observar las estrellas. Cerca de observatorios turísticos y del centro de Vicuña.",
                        "/img/img-tarjeta-9.avif", 6, 3, 2, 110, false,
                        List.of("Terraza", "Piscina", "WiFi", "Estacionamiento")),
                //13
                propiedad("Loft en barrio Lastarria", TipoPropiedad.loft, "Santiago, Santiago", 58000, 1L,
                        "Loft de doble altura en uno de los barrios más tradicionales de Santiago, a pasos del cerro Santa Lucía, museos y metro.",
                        "/img/img-tarjeta-4.avif", 2, 1, 1, 42, false,
                        List.of("WiFi de fibra óptica", "Escritorio de trabajo", "Cocina equipada")),
                //14
                propiedad("Departamento estudio cerca de la universidad", TipoPropiedad.departamento, "Temuco, La Araucanía", 39000, 2L,
                        "Departamento tipo estudio, amoblado y luminoso, a cinco minutos de las principales universidades. Buena opción para estadías de estudio o trabajo.",
                        "/img/img-tarjeta-8.avif", 2, 1, 1, 32, false,
                        List.of("WiFi", "Cocina equipada", "Lavandería en el edificio")),
                //15
                propiedad("Cabaña con vista a la Patagonia", TipoPropiedad.cabana, "Puerto Natales, Magallanes", 120000, 1L,
                        "Cabaña frente al fiordo Última Esperanza, punto de partida para visitar el Parque Nacional Torres del Paine. Calefacción central y vista a las montañas.",
                        "/img/img-tarjeta-6.avif", 4, 2, 1, 60, false,
                        List.of("Calefacción central", "WiFi", "Estacionamiento", "Cocina equipada")),
                //16
                propiedad("Casa patrimonial en el cerro", TipoPropiedad.casa, "Valparaíso, Valparaíso", 76000, 2L,
                        "Casa restaurada en el cerro Alegre, a pasos de ascensores, miradores y del barrio patrimonial. Vista a la bahía desde la terraza.",
                        "/img/img-tarjeta-2.avif", 5, 3, 2, 100, false,
                        List.of("Terraza con vista", "WiFi", "Cocina equipada")),
                //17
                propiedad("Parcela de agrado con piscina", TipoPropiedad.parcela, "Limache, Valparaíso", 95000, 1L,
                        "Parcela con amplios jardines, piscina y quincho techado, a una hora de Santiago. Perfecta para reuniones familiares de fin de semana.",
                        "/img/img-tarjeta-7.avif", 8, 4, 2, 160, false,
                        List.of("Piscina", "Quincho techado", "Estacionamiento", "WiFi")),
                //18
                propiedad("Departamento con terraza en la costanera", TipoPropiedad.departamento, "Antofagasta, Antofagasta", 62000, 2L,
                        "Departamento con terraza amplia sobre la costanera, cerca del centro y de la playa Balneario Municipal. Edificio con gimnasio y conserjería.",
                        "/img/img-tarjeta-1.avif", 4, 2, 2, 70, false,
                        List.of("Gimnasio", "Conserjería", "Estacionamiento", "WiFi")),
                //19
                propiedad("Casa frente al mar en Chiloé", TipoPropiedad.casa, "Castro, Los Lagos", 88000, 1L,
                        "Casa de madera con ventanal hacia el mar, cerca de los palafitos y de las iglesias patrimoniales de Chiloé. Estufa a leña y cocina equipada.",
                        "/img/img-tarjeta-5.avif", 6, 3, 2, 115, false,
                        List.of("Estufa a leña", "Cocina equipada", "Estacionamiento", "WiFi")),
                //20
                propiedad("Loft moderno frente a la costanera", TipoPropiedad.loft, "Puerto Montt, Los Lagos", 52000, 2L,
                        "Loft moderno con vista al seno de Reloncaví, cerca del terminal de buses y de Angelmó. Ideal para viajes de trabajo o como base para recorrer el sur.",
                        "/img/img-tarjeta-4.avif", 2, 1, 1, 38, false,
                        List.of("WiFi de fibra óptica", "Escritorio de trabajo", "Calefacción"))
        ));
        log.info(">>> DataInitializer: {} propiedades insertadas correctamente.",
                propiedadesRepository.count());
    }

    //arma una propiedad disponible; el id lo genera MySQL
    private Propiedades propiedad(String titulo, TipoPropiedad tipo, String ubicacion, int precioNoche,
                                  Long idAnfitrion, String descripcion, String imagen, int capacidad,
                                  int habitaciones, int banos, int superficie, boolean destacada,
                                  List<String> servicios) {
        return new Propiedades(
                null, titulo, descripcion, ubicacion, BigDecimal.valueOf(precioNoche), idAnfitrion,
                tipo, true, imagen, capacidad, habitaciones, banos, superficie, servicios, destacada
        );
    }
}