package com.home_rental_solution.ms_propiedades.config;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PropiedadesRepository propiedadesRepository;

    @Override
    public void run(String... args){
        //evita insertar datos duplicados en cada arranque
        if (propiedadesRepository.count() > 0){
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial");
            return;
        }
        log.info(">>> DataInitializer: BD vacia detectada, insertando propiedades de prueba");
        propiedadesRepository.save(new Propiedades(
                null,
                "Departamento Santiago",
                "Departamento con 2 dormitorios y 1 baño",
                "Santiago Centro",
                new BigDecimal("45000000"),
                1,
                "departamento",
                true
        ));propiedadesRepository.save(new Propiedades(
                null,
                "Casa en Providencia",
                "Casa de 5 dormitorios cercana al metro",
                "Providencia",
                new BigDecimal("345000000"),
                1,
                "casa",
                true
        ));propiedadesRepository.save(new Propiedades(
                null,
                "Casa en Maipú",
                "Casa con 3 dormitorios, patio y estacionamiento",
                "Maipú",
                new BigDecimal("78000000"),
                2,
                "casa",
                true
        ));
        log.info(">>> DataInitializer: {} propiedades insertadas correctamente.",
        propiedadesRepository.count());
    }
}
