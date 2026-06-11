package com.home_rental_solution.ms_propiedades.assemblers;

import com.home_rental_solution.ms_propiedades.controller.PropiedadesControllerV2;
import com.home_rental_solution.ms_propiedades.dto.PropiedadesResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PropiedadesModelAssembler implements RepresentationModelAssembler<PropiedadesResponseDTO,
        EntityModel<PropiedadesResponseDTO>> {

    @Override
    public EntityModel<PropiedadesResponseDTO> toModel (PropiedadesResponseDTO dto){
        return EntityModel.of(
                dto,
                //enlace al recurso individual
                linkTo(methodOn(PropiedadesControllerV2.class)
                        .getPorId(dto.getId()))
                        .withSelfRel(),

                //Enlace al listado completo de propiedades
                linkTo(methodOn(PropiedadesControllerV2.class)
                        .getTodas())
                        .withRel("propiedades"),

                //Enlace extra dinamico para conmutar su estado de disponibilidad
                linkTo(methodOn(PropiedadesControllerV2.class)
                        .cambiarEstado(dto.getId()))
                        .withRel("cambiar-estado")
                );
    }
}
