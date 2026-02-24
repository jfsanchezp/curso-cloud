package es.um.atica.umufly.vuelos.adaptors.api.rest;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.api.rest.dto.VueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.mapper.ApiRestMapper;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class VuelosModelAssembler implements RepresentationModelAssembler<Vuelo, VueloDTO> {

	@Override
	public VueloDTO toModel( Vuelo entity ) {
		return ApiRestMapper.vueloToDTO( entity );
	}

}
