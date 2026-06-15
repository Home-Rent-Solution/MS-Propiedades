package com.home_rental_solution.ms_propiedades.service;

import com.home_rental_solution.ms_propiedades.client.AnfitrionClient;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesRequestDTO;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesResponseDTO;
import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PropiedadesServiceTest {

    @Autowired
    private PropiedadesService propiedadesService;

    @MockitoBean
    private PropiedadesRepository propiedadesRepository;

    @MockitoBean
    private AnfitrionClient anfitrionClient;

    //Test CRUD

    @Test
    public void testMostrarPropiedades(){
        Propiedades propiedades = new Propiedades(
                1L,
                "Casa linda",
                "Bella",
                "Paine",
                BigDecimal.valueOf(45000000),
                2L,
                Propiedades.TipoPropiedad.casa,
                true
        );
        when (propiedadesRepository.findAll()).thenReturn(List.of(propiedades));
        List<PropiedadesResponseDTO> resultado = propiedadesService.mostrarPropiedades();
        assertNotNull(resultado);
        assertEquals(
                1,
                resultado.size()
        );
        assertEquals(
                "Casa linda",
                resultado.get(0).getTitulo()
        );
    }

    @Test
    public void testMostrarPorId_Success() {
        Propiedades propiedad = new Propiedades(
                1L,
                "Casa Playa",
                "Grande",
                "Pichilemu",
                BigDecimal.valueOf(500000000),
                2L,
                Propiedades.TipoPropiedad.casa,
                true
        );
        when(propiedadesRepository.findById(1L)).thenReturn(Optional.of(propiedad));
        PropiedadesResponseDTO resultado = propiedadesService.mostrarPorId(1L);
        assertNotNull(resultado);
        assertEquals(
                "Casa Playa",
                resultado.getTitulo()
        );
    }

    @Test
    public void testMostrarPorId_NotFound() {
        when(propiedadesRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propiedadesService.mostrarPorId(99L);
        });
        assertEquals(
                "La propiedad con ID: 99 no existe",
                exception.getMessage()
        );
    }

    @Test
    public void testSave_Success() {
        PropiedadesRequestDTO request = new PropiedadesRequestDTO(
                "Estudio",
                "Monoambiente",
                "Centro",
                BigDecimal.valueOf(150000000),
                2L,
                Propiedades.TipoPropiedad.casa
        );

        Propiedades propiedadGuardada = new Propiedades(
                1L,
                request.getTitulo(),
                request.getDescripcion(),
                request.getUbicacion(),
                request.getPrecio(),
                request.getIdAnfitrion(),
                request.getTipo(),
                false
        );

        // Simular que el anfitrión es válido y que el repositorio guarda con éxito
        when(anfitrionClient.validarAnfitrion(2L)).thenReturn(true);
        when(propiedadesRepository.save(any(Propiedades.class))).thenReturn(propiedadGuardada);
        PropiedadesResponseDTO resultado = propiedadesService.save(request);
        assertNotNull(resultado);
        assertEquals(
                1L,
                resultado.getId())
        ;
        assertFalse(resultado.isDisponible());
    }

    @Test
    public void testSave_AnfitrionNoVerificado() {
        PropiedadesRequestDTO request = new PropiedadesRequestDTO(
                "Estudio",
                "Monoambiente",
                "Centro",
                BigDecimal.valueOf(150000000),
                2L,
                Propiedades.TipoPropiedad.casa
        );

        // Declaramos en el cliente de Feign que NO está verificado (false)
        when(anfitrionClient.validarAnfitrion(2L)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propiedadesService.save(request);
        });
        assertTrue(exception.getMessage().contains("no esta verificado"));
    }

    @Test
    public void testSave_AnfitrionNotFoundEnFeign() {
        PropiedadesRequestDTO request = new PropiedadesRequestDTO(
                "Estudio",
                "Monoambiente",
                "Centro",
                BigDecimal.valueOf(150000000),
                2L,
                Propiedades.TipoPropiedad.casa
        );

        // Simulamos un error 404 de Feign de manera limpia
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);
        when(anfitrionClient.validarAnfitrion(2L)).thenThrow(feignException);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propiedadesService.save(request);
        });
        assertTrue(exception.getMessage().contains("no existe en ms-anfitriones"));
    }

    @Test
    public void testEditar_Success() {
        Propiedades propiedadExistente = new Propiedades(
                1L,
                "Viejo",
                "Desc",
                "Ubi",
                BigDecimal.valueOf(10),
                2L,
                Propiedades.TipoPropiedad.casa,
                true
        );
        PropiedadesRequestDTO nuevosDatos = new PropiedadesRequestDTO(
                "Nuevo",
                "Nueva Desc",
                "Nueva Ubi",
                BigDecimal.valueOf(20),
                2L,
                Propiedades.TipoPropiedad.casa
        );

        when(propiedadesRepository.findById(1L)).thenReturn(Optional.of(propiedadExistente));
        when(anfitrionClient.validarAnfitrion(2L)).thenReturn(true);
        when(propiedadesRepository.save(any(Propiedades.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        PropiedadesResponseDTO resultado = propiedadesService.editar(
                1L,
                nuevosDatos
        );
        assertEquals(
                "Nuevo",
                resultado.getTitulo()
        );
        assertEquals(
                BigDecimal.valueOf(20),
                resultado.getPrecio()
        );
    }

    @Test
    public void testBorrar_Success() {
        when(propiedadesRepository.existsById(1L)).thenReturn(true);
        propiedadesService.borrar(1L);
        verify(propiedadesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testBorrar_NotFound() {
        when(propiedadesRepository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {propiedadesService.borrar(99L);
        });
    }

    //TEST de los extras

    @Test
    public void testMostrarPorAnfitrion() {
        Propiedades p = new Propiedades(
                1L,
                "T1",
                "D1",
                "U1",
                BigDecimal.valueOf(50),
                3L,
                Propiedades.TipoPropiedad.casa,
                true
        );
        when(propiedadesRepository.findByIdAnfitrion(3L)).thenReturn(List.of(p));
        List<PropiedadesResponseDTO> resultado = propiedadesService.mostrarPorAnfitrion(3L);
        assertEquals(
                1,
                resultado.size()
        );
        assertEquals(
                3L,
                resultado.get(0).getIdAnfitrion()
        );
    }

    @Test
    public void testMostrarPorPrecio_Valido() {
        Propiedades p = new Propiedades(
                1L,
                "T1",
                "D1",
                "U1",
                BigDecimal.valueOf(150),
                2L, Propiedades.TipoPropiedad.casa,
                true
        );
        when(propiedadesRepository.findByPrecioBetween(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200))
        ).thenReturn(List.of(p));
        List<PropiedadesResponseDTO> resultado = propiedadesService.mostrarPorPrecio(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200)
        );
        assertEquals(
                1,
                resultado.size()
        );
    }

    @Test
    public void testMostrarPorPrecio_Invalido() {
        // Precio mínimo (20000000) mayor al máximo (10000000) -> Debe fallar
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propiedadesService.mostrarPorPrecio(
                    BigDecimal.valueOf(20000000),
                    BigDecimal.valueOf(10000000)
            );
        });

        assertEquals(
                "El precio minimo no puede ser mayor que el maximo",
                exception.getMessage()
        );
    }

    @Test
    public void testMostrarUbicacionPrecio_Invalido() {
        assertThrows(RuntimeException.class, () -> {
            propiedadesService.mostrarUbicacionPrecio(
                    "Maipu",
                    BigDecimal.valueOf(50000000),
                    BigDecimal.valueOf(10000000)
            );
        });
    }

    @Test
    public void testMostrarDisponibles() {
        Propiedades p = new Propiedades(
                1L,
                "T1",
                "D1",
                "U1",
                BigDecimal.valueOf(50),
                2L,
                Propiedades.TipoPropiedad.casa,
                true
        );
        when(propiedadesRepository.findByDisponibleTrue()).thenReturn(List.of(p));
        List<PropiedadesResponseDTO> resultado = propiedadesService.mostrarDisponibles();
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.get(0).isDisponible());
    }

    @Test
    public void testCambiarEstado() {
        Propiedades propiedadInicial = new Propiedades(
                1L,
                "T1",
                "D1",
                "U1",
                BigDecimal.valueOf(50),
                2L,
                Propiedades.TipoPropiedad.casa,
                true
        );
        when(propiedadesRepository.findById(1L)).thenReturn(Optional.of(propiedadInicial));
        when(propiedadesRepository.save(any(Propiedades.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // El estado original es 'true', al cambiar debería ser 'false'
        PropiedadesResponseDTO resultado = propiedadesService.cambiarEstado(1L);
        assertFalse(resultado.isDisponible());
    }

    // Helper interno estático para evitar duplicar código de instanciación en los retornos de guardado
    private static class PropertiesMockBuilder {
        public Propiedades buildSaved(Long id, PropiedadesRequestDTO dto) {
            return new Propiedades(id, dto.getTitulo(), dto.getDescripcion(), dto.getUbicacion(), dto.getPrecio(), dto.getIdAnfitrion(), dto.getTipo(), false);
        }
    }

}
