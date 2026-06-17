package com.home_rental_solution.ms_propiedades.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesRequestDTO;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesResponseDTO;
import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.service.PropiedadesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.*;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(PropiedadesController.class)
public class PropiedadesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropiedadesService propiedadesService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PropiedadesResponseDTO propiedadResponse;
    private PropiedadesRequestDTO propiedadRequest;

    @BeforeEach
    void setUp() {
        // Configura los objetos DTO de ejemplo antes de cada prueba
        propiedadResponse = new PropiedadesResponseDTO(
                1L,
                "Casa de Playa",
                "Hermosa vista al mar",
                "Pichilemu",
                BigDecimal.valueOf(150000),
                2L,
                Propiedades.TipoPropiedad.casa,
                true
        );

        propiedadRequest = new PropiedadesRequestDTO(
                "Casa de Playa",
                "Hermosa vista al mar",
                "Pichilemu",
                BigDecimal.valueOf(150000),
                2L,
                Propiedades.TipoPropiedad.casa
        );
    }

    // TESTS CRUD

    @Test
    public void testGetTodas() throws Exception {
        when(propiedadesService.mostrarPropiedades()).thenReturn(List.of(propiedadResponse));

        mockMvc.perform(get("/api/v1/propiedades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Casa de Playa"))
                .andExpect(jsonPath("$[0].ubicacion").value("Pichilemu"))
                .andExpect(jsonPath("$[0].precio").value(150000))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    public void testGetPorId() throws Exception {
        when(propiedadesService.mostrarPorId(1L)).thenReturn(propiedadResponse);

        mockMvc.perform(get("/api/v1/propiedades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Casa de Playa"))
                .andExpect(jsonPath("$.precio").value(150000));
    }

    @Test
    public void testPostPropiedad() throws Exception {
        when(propiedadesService.save(any(PropiedadesRequestDTO.class))).thenReturn(propiedadResponse);

        // Tu controlador responde con HttpStatus.CREATED (201) al guardar con éxito
        mockMvc.perform(post("/api/v1/propiedades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propiedadRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Casa de Playa"));
    }

    @Test
    public void testPutPropiedad() throws Exception {
        when(propiedadesService.editar(eq(1L), any(PropiedadesRequestDTO.class))).thenReturn(propiedadResponse);

        mockMvc.perform(put("/api/v1/propiedades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propiedadRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Casa de Playa"));
    }

    @Test
    public void testDeletePropiedad() throws Exception {
        doNothing().when(propiedadesService).borrar(1L);

        // Tu controlador responde con HttpStatus.NO_CONTENT (204) al eliminar con éxito
        mockMvc.perform(delete("/api/v1/propiedades/1"))
                .andExpect(status().isNoContent());

        verify(propiedadesService, times(1)).borrar(1L);
    }

    // TESTS MÉTODOS EXTRAS

    @Test
    public void testGetPorAnfitrion() throws Exception {
        when(propiedadesService.mostrarPorAnfitrion(2L)).thenReturn(List.of(propiedadResponse));

        mockMvc.perform(get("/api/v1/propiedades/anfitrion/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAnfitrion").value(2));
    }

    @Test
    public void testGetPorPrecio() throws Exception {
        when(propiedadesService.mostrarPorPrecio(BigDecimal.valueOf(100000), BigDecimal.valueOf(200000)))
                .thenReturn(List.of(propiedadResponse));

        mockMvc.perform(get("/api/v1/propiedades/precio")
                        .param("min", "100000")
                        .param("max", "200000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].precio").value(150000));
    }

    @Test
    public void testGetPorTipo() throws Exception {
        when(propiedadesService.mostrarPorTipo(Propiedades.TipoPropiedad.casa)).thenReturn(List.of(propiedadResponse));

        mockMvc.perform(get("/api/v1/propiedades/tipo/casa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("casa"));
    }

    @Test
    public void testGetDisponibles() throws Exception {
        when(propiedadesService.mostrarDisponibles()).thenReturn(List.of(propiedadResponse));

        mockMvc.perform(get("/api/v1/propiedades/disponibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    public void testGetUbicacionPrecio() throws Exception {
        when(propiedadesService.mostrarUbicacionPrecio("Pichilemu", BigDecimal.valueOf(100000), BigDecimal.valueOf(200000)))
                .thenReturn(List.of(propiedadResponse));

        mockMvc.perform(get("/api/v1/propiedades/buscar")
                        .param("ubicacion", "Pichilemu")
                        .param("precioMin", "100000")
                        .param("precioMax", "200000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ubicacion").value("Pichilemu"));
    }

    @Test
    public void testCambiarEstado() throws Exception {
        // Simulamos que al cambiar el estado, la propiedad cambia disponible a false
        PropiedadesResponseDTO propiedadModificada = new PropiedadesResponseDTO(
                1L, "Casa de Playa", "Hermosa vista al mar", "Pichilemu",
                BigDecimal.valueOf(150000), 2L, Propiedades.TipoPropiedad.casa, false
        );

        when(propiedadesService.cambiarEstado(1L)).thenReturn(propiedadModificada);

        mockMvc.perform(put("/api/v1/propiedades/1/estado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disponible").value(false));
    }
}
