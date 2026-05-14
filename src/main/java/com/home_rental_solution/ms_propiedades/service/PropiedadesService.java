package com.home_rental_solution.ms_propiedades.service;

import com.home_rental_solution.ms_propiedades.client.AnfitrionClient;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesRequestDTO;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesResponseDTO;
import com.home_rental_solution.ms_propiedades.model.Propiedades;
import com.home_rental_solution.ms_propiedades.repository.PropiedadesRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropiedadesService {

    private final PropiedadesRepository propiedadesRepository;

    @Autowired
    private final AnfitrionClient anfitrionClient;

    //Mapeo Entidad -> ResponseDTO
    private PropiedadesResponseDTO mapToDTO(Propiedades propiedad){
        return new PropiedadesResponseDTO(
                propiedad.getIdPropiedad(),
                propiedad.getTitulo(),
                propiedad.getDescripcion(),
                propiedad.getUbicacion(),
                propiedad.getPrecio(),
                propiedad.getIdAnfitrion(),
                propiedad.getTipo(),
                propiedad.isDisponible()
        );
    }

    //Mapeo RequestDTO -> Entidad
    private Propiedades mapToEntity(PropiedadesRequestDTO dto){
        return new Propiedades(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getUbicacion(),
                dto.getPrecio(),
                dto.getIdAnfitrion(),
                dto.getTipo(),
                false
        );
    }

    //Validacion con FeignClients
    private void validarAnfitrion(int idAnfitrion){
        try{
            boolean verificado = anfitrionClient.validarAnfitrion(idAnfitrion);
            log.info(">>> Anfitrion {} validado correctamente (Feign Client)", idAnfitrion);
            if (!verificado){
                throw  new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no esta verificado");
            }
        } catch (FeignException.NotFound e){
            throw new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no existe en ms-anfitriones");
        } catch (FeignException e){
            throw new RuntimeException("No se puede conectar con ms-anfitriones: " + e.getMessage());
        }
    }

    //***CRUD**
    //GET /propiedades
    public List<PropiedadesResponseDTO> mostrarPropiedades(){
        return propiedadesRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //GET /propiedades/id
    public PropiedadesResponseDTO mostrarPorId(int id){
        Propiedades propiedad = propiedadesRepository.findById(id).orElseThrow(() -> new RuntimeException("La propiedad " +
                "con ID: " + id + " no existe"));
        return mapToDTO(propiedad);
    }

    //POST /propiedades
    public PropiedadesResponseDTO save (PropiedadesRequestDTO dto){
        Propiedades nuevaPropiedad = mapToEntity(dto);
        return mapToDTO(propiedadesRepository.save(nuevaPropiedad));
    }

    //PUT /propiedades/Id
    public PropiedadesResponseDTO editar(Integer id, PropiedadesRequestDTO dto) {
        Propiedades propiedadExistente = propiedadesRepository.findById(id).orElseThrow(() -> new RuntimeException("La " +
                "propiedad con ID: " + id + " no existe"));
        propiedadExistente.setTitulo(dto.getNombre());
        propiedadExistente.setDescripcion(dto.getDescripcion());
        propiedadExistente.setUbicacion(dto.getUbicacion());
        propiedadExistente.setPrecio(dto.getPrecio());
        propiedadExistente.setIdAnfitrion(dto.getIdAnfitrion());
        propiedadExistente.setTipo(dto.getTipo());
        // disponible no se toca porque se maneja con PUT
        return mapToDTO(propiedadesRepository.save(propiedadExistente));
    }

    // DELETE /propiedad/id
    public void borrar (int id){
        if (!propiedadesRepository.existsById(id)){
            throw new RuntimeException("La propiedad con ID: " + id + " no existe");
        }
        propiedadesRepository.deleteById(id);
    }

    //***EXTRAS***
    //GET /propiedades/anfitrion/id
    public List<PropiedadesResponseDTO> mostrarPorAnfitrion(int idAnfitrion){
        return propiedadesRepository.findByIdAnfitrion(idAnfitrion).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //GET /propiedades/precio
    public List<PropiedadesResponseDTO> mostrarPorPrecio(BigDecimal min, BigDecimal max){
        if (min.compareTo(max) > 0){
            throw new RuntimeException("El precio minimo no puede ser mayor que el maximo");
        }
        return propiedadesRepository.findByPrecioBetween(min, max).stream().map(this::mapToDTO).collect(Collectors
                .toList());
    }

    //GET /propiedades/tipo
    public List<PropiedadesResponseDTO> mostrarPorTipo(Propiedades.TipoPropiedad tipo){
        return propiedadesRepository.findByTipo(tipo).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //GET /propiedades/buscar?ubicacion=&precioMin=&precioMax=
    public List<PropiedadesResponseDTO> mostrarUbicacionPrecio(String ubicacion, BigDecimal precioMin,
                                                               BigDecimal precioMax){
        if (precioMin.compareTo(precioMax) > 0){
            throw new RuntimeException("El precio minimo no debe ser mayor que el maximo");
        }
        return propiedadesRepository.findByUbicacionContainingIgnoreCaseAndPrecioBetween(ubicacion, precioMin, precioMax)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //GET /propiedades/disponibles
    public List<PropiedadesResponseDTO> mostrarDisponibles(){
        return propiedadesRepository.findByDisponibleTrue().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //PUT /propiedades/id/estad
    public PropiedadesResponseDTO cambiarEstado(int id){
        Propiedades propiedad = propiedadesRepository.findById(id).orElseThrow(() -> new RuntimeException("La propiedad " +
                "con ID: " + id + " no existe"));
        propiedad.setDisponible(!propiedad.isDisponible());
        return mapToDTO(propiedadesRepository.save(propiedad));
    }
}
