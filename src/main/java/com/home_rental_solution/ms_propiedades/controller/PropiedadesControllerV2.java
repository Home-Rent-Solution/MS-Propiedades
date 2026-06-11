package com.home_rental_solution.ms_propiedades.controller;

import com.home_rental_solution.ms_propiedades.assemblers.PropiedadesModelAssembler;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesRequestDTO;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesResponseDTO;
import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.service.PropiedadesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/propiedades")
@RequiredArgsConstructor
@Tag(name = "Propiedades", description = "Controlador principal para la gestión, filtros y búsquedas avanzadas" +
        " de propiedades de alquiler")
public class PropiedadesControllerV2 {

    private final PropiedadesService propiedadesService;
    private final PropiedadesModelAssembler assembler;

    //***CRUD***
    //GET /propiedades
    @GetMapping(
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Obtener todas las propiedades",
            description = "Devuelve una lista completa de todas las propiedades registradas en el sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de propiedades recuperada con éxito",
            content = @Content(
                    mediaType = "application/hal + json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = PropiedadesResponseDTO.class)
                    )
            )
    )
    public CollectionModel<EntityModel<PropiedadesResponseDTO>> getTodas() {
        List<EntityModel<PropiedadesResponseDTO>> propiedades = propiedadesService
                .mostrarPropiedades()
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(propiedades,linkTo(methodOn(PropiedadesControllerV2.class).getTodas()).withSelfRel());
    }

    //GET /propiedades/id
    @GetMapping(
            value = "{id}",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Obtener una propiedad por ID",
            description = "Busca y devuelve los detalles de una propiedad específica utilizando su identificador único."
    )
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "Propiedad encontrada exitosamente",
                    content = @Content(
                            mediaType = "application/hal + json",
                            schema = @Schema(
                            implementation = PropiedadesResponseDTO.class)
                    )
            )
                    ,@ApiResponse(
                            responseCode = "400",
                    description = "La propiedad solicitada no existe en el sistema",
                    content = @Content)
            })

    public EntityModel<PropiedadesResponseDTO> getPorId(@Parameter(
            description = "ID numerico de la propiedad a buscar",
            example = "1",
            required = true) @PathVariable Long id) {
        PropiedadesResponseDTO dto = propiedadesService.mostrarPorId(id);
        return assembler.toModel(dto);
    }

    //POST /propiedades
    @PostMapping(
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Registrar una nueva propiedad",
            description = "Crea una propiedad en el sistema. Valida mediante OpenFeign que el ID del anfitrión" +
                    " exista y se encuentre verificado en ms-anfitriones."
    )
    @ApiResponses(
            value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Propiedad creada correctamente",
                    content = @Content(
                            mediaType = "application/hal + json",
                            schema = @Schema(
                            implementation = PropiedadesResponseDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los datos de entrada o el anfitrión no es válido/no está" +
                            " verificado",
                    content = @Content
            )
    })

    public ResponseEntity<EntityModel<PropiedadesResponseDTO>> postPropiedad(
            @Valid @RequestBody PropiedadesRequestDTO dto) {
        PropiedadesResponseDTO nuevoDTO = propiedadesService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(nuevoDTO));
    }

    //PUT /propiedades/id
    @PutMapping(
            value = "{id}",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Actualizar una propiedad existente",
            description = "Modifica los datos de una propiedad utilizando su ID. Los campos automáticos o de estado" +
                    " se preservan de forma segura."
    )
    @ApiResponses(
            value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Propiedad actualizada con éxito",
                    content = @Content(
                            mediaType = "application/hal + json",
                            schema = @Schema(
                            implementation = PropiedadesResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID no encontrado o datos de actualización inválidos",
                    content = @Content
            )
    })
    public ResponseEntity<EntityModel<PropiedadesResponseDTO>> putPropiedad(
            @PathVariable Long id, @Valid @RequestBody PropiedadesRequestDTO dto) {
        PropiedadesResponseDTO editadoDto = propiedadesService.editar(id, dto);
        return ResponseEntity.ok(assembler.toModel(editadoDto));
    }

    //DELETE /propiedades/id
    @DeleteMapping(
            value = "{id}",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(summary = "Eliminar una propiedad", description = "Remueve permanentemente una propiedad del" +
            " sistema de base de datos a través de su identificador único.")
    @ApiResponses(
            value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Propiedad eliminada exitosamente (Sin contenido de retorno)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "El ID de la propiedad especificada no existe",
                    content = @Content)
    })

    public ResponseEntity<Void> deletePropiedad(@PathVariable Long id) {
        propiedadesService.borrar(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    //***EXTRAS***
    //GET /propiedades/id/anfitrion
    @GetMapping(
            value = "/anfitrion/{idAnfitrion}",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Listar propiedades por Anfitrión",
            description = "Recupera todas las propiedades asociadas a un identificador único de anfitrión."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de propiedades del anfitrión devuelta con éxito",
            content = @Content(
                    mediaType = "application/hal + json",
                    array = @ArraySchema(
                    schema = @Schema(implementation = PropiedadesResponseDTO.class)
                    )
            )
    )
    public CollectionModel<EntityModel<PropiedadesResponseDTO>> getPorAnfitrion(
            @Parameter(
                    description = "ID del anfitrión para filtrar sus alojamientos",
                    example = "5",
                    required = true
            )
            @PathVariable Long idAnfitrion) {
        List<EntityModel<PropiedadesResponseDTO>> propiedades = propiedadesService
                .mostrarPorAnfitrion(idAnfitrion)
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(propiedades);
    }

    //GET /propiedades/precio
    @GetMapping(
            value = "/precio",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Filtrar propiedades por rango de precio",
            description = "Devuelve todas las propiedades cuyo precio por noche se encuentre dentro del rango mínimo" +
                    " y máximo enviado, ordenadas de menor a mayor."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Resultados del filtro por rango de precios",
            content = @Content(
                    mediaType = "application/hal + json",
                    array = @ArraySchema(
                    schema = @Schema(implementation = PropiedadesResponseDTO.class)
                    )
            )
    )
    public CollectionModel<EntityModel<PropiedadesResponseDTO>> getPorPrecio(
            @Parameter(
                    description = "Monto de precio mínimo",
                    example = "50.00",
                    required = true
            )
            @RequestParam BigDecimal min,
            @Parameter(
                    description = "Monto de precio máximo",
                    example = "250.00",
                    required = true
            )
            @RequestParam BigDecimal max) {
        List<EntityModel<PropiedadesResponseDTO>> propiedades = propiedadesService
                .mostrarPorPrecio(min, max)
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(propiedades);
    }

    //GET /propiedades/tipo
    @GetMapping(
            value = "/tipo/{tipo}",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Filtrar propiedades por tipo",
            description = "Obtiene una lista de propiedades que coincidan exactamente con el tipo de alojamiento" +
                    " solicitado (ej. CASA, DEPARTAMENTO, HABITACION)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Propiedades filtradas por tipo encontradas",
            content = @Content(
                    mediaType = "application/hal + json",
                    array = @ArraySchema(
                    schema = @Schema(implementation = PropiedadesResponseDTO.class)
                    )
            )
    )
    public CollectionModel<EntityModel<PropiedadesResponseDTO>> getPorTipo(
            @Parameter(
                    description = "Enumerador correspondiente al tipo de propiedad",
                    required = true
            )
            @PathVariable Propiedades.TipoPropiedad tipo) {
        List<EntityModel<PropiedadesResponseDTO>> propiedades = propiedadesService
                .mostrarPorTipo(tipo)
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(propiedades);
    }

    //GET /propiedades/disponibles
    @GetMapping(
            value = "/disponibles",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Obtener todas las propiedades disponibles",
            description = "Lista de manera directa todos los alojamientos que se encuentran con el flag de" +
                    " disponibilidad activo (disponible = true)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de propiedades disponibles recuperada",
            content = @Content(
                    mediaType = "application/hal + json",
                    array = @ArraySchema(
                    schema = @Schema(implementation = PropiedadesResponseDTO.class)
                    )
            )
    )
    public CollectionModel<EntityModel<PropiedadesResponseDTO>> getDisponibles() {
        List<EntityModel<PropiedadesResponseDTO>> propiedades = propiedadesService
                .mostrarDisponibles()
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(propiedades);
    }

    //GET /propiedades/buscar?ubicacion=algo&precioMin=algo&precioMax=algo
    @GetMapping(
            value = "/buscar",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Búsqueda avanzada combinada",
            description = "Permite filtrar de manera simultánea por una coincidencia de texto en la ubicación" +
                    " (ignora mayúsculas/minúsculas) junto con un rango de precio específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Resultados que cumplen con los criterios de búsqueda compleja",
            content = @Content(
                    mediaType = "application/hal + json",
                    array = @ArraySchema(
                    schema = @Schema(
                            implementation = PropiedadesResponseDTO.class)
                    )
            )
    )
    public CollectionModel<EntityModel<PropiedadesResponseDTO>> getUbicacionPrecio(
            @Parameter(
                    description = "Texto o coincidencia de la ubicación/ciudad",
                    example = "Santiago",
                    required = true)
            @RequestParam String ubicacion,
            @Parameter(
                    description = "Filtro de precio mínimo para la búsqueda",
                    example = "30.00",
                    required = true)
            @RequestParam BigDecimal precioMin,
            @Parameter(
                    description = "Filtro de precio máximo para la búsqueda",
                    example = "180.00",
                    required = true)
            @RequestParam BigDecimal precioMax) {
        List<EntityModel<PropiedadesResponseDTO>> propiedades = propiedadesService
                .mostrarUbicacionPrecio(ubicacion, precioMin, precioMax)
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(propiedades);
    }

    //PUT /propiedades/estado--para cambiar el estado
    @PutMapping(
            value = "{id}/estado",
            produces = MediaTypes.HAL_JSON_VALUE
    )
    @Operation(
            summary = "Conmutar estado de disponibilidad",
            description = "Invierte de forma dinámica el estado actual de la propiedad. Si estaba disponible (true)," +
                    " pasa a no disponible (false) y viceversa."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado de disponibilidad modificado correctamente",
                    content = @Content(
                            mediaType = "application/hal + json",
                            schema = @Schema(
                            implementation = PropiedadesResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "El ID proporcionado no corresponde a ninguna propiedad activa",
                    content = @Content)
    })
    public ResponseEntity<EntityModel<PropiedadesResponseDTO>> cambiarEstado(
            @Parameter(
                    description = "ID de la propiedad a la que se le conmutará la disponibilidad",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        PropiedadesResponseDTO modificadoDto = propiedadesService.cambiarEstado(id);
        return ResponseEntity.ok(assembler.toModel(modificadoDto));
    }
}
