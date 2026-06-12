package com.home_rental_solution.ms_propiedades;

import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import com.home_rental_solution.ms_propiedades.service.PropiedadesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PropiedadesServiceTest {

    @Autowired
    private PropiedadesService propiedadesService;

    @MockitoBean
    private PropiedadesRepository propiedadesRepository;

    @Test
    public void testFindAll(){
        when (propiedadesRepository.findAll()).thenReturn(List.of(new Propiedades(
                1,
                "titulo",
                "descripcion",
                "ubicacion",
                15000,
                2,
                "casa",
                false))
        );
    }
}
