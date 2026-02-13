package es.um.atica.umufly.vuelos.adaptors.api.rest.v1;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.VueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.mapper.VueloMapper;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliado;

@Component( "v1.vuelosModelAssembler" )
public class VuelosModelAssembler implements RepresentationModelAssembler<VueloAmpliado, VueloDTO> {

	@Override
	public VueloDTO toModel( VueloAmpliado entity ) {
		return VueloMapper.vueloRestDTOFromApplicationDTO( entity );
	}

}
