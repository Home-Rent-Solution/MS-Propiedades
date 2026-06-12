package com.home_rental_solution.ms_propiedades;

import com.home_rental_solution.ms_propiedades.model.Propiedades;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Profile({"dev", "test"})
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    @Autowired
    private final PropiedadesRepository propiedadesRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();
        Propiedades.TipoPropiedad[] tipos = Propiedades.TipoPropiedad.values();

        //generamos 20 propiedades
        for (int i = 0; i < 20; i++){
            Propiedades propiedad = new Propiedades();
            propiedad.setTitulo(faker.house().room() + "confortable en" + faker.address().streetName());
            propiedad.setDescripcion(faker.lorem().sentence(10));
            propiedad.setUbicacion(faker.address().fullAddress());
            propiedad.setPrecio(BigDecimal.valueOf(faker.number().numberBetween(1000000, 100000000)));
            propiedad.setDisponible(faker.bool().bool());
            propiedad.setIdAnfitrion((long) faker.number().numberBetween(1, 15));
            propiedad.setTipo(faker.options().option(tipos));
            propiedadesRepository.save(propiedad);
        }
        log.info(">> ms-propiedades: ¡Base de datos poblada con DataFaker exitosamente!");
    }
}
