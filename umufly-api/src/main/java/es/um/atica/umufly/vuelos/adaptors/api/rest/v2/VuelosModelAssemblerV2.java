package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import java.util.UUID;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.LinkService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.VueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper.ApiRestV2Mapper;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;

@Component
public class VuelosModelAssemblerV2 implements RepresentationModelAssembler<VueloAmpliadoDTO, VueloDTO> {

	private final LinkService linkService;

	public VuelosModelAssemblerV2( LinkService linkService ) {
		this.linkService = linkService;
	}

	@Override
	public VueloDTO toModel( VueloAmpliadoDTO entity ) {
		VueloDTO vuelo = ApiRestV2Mapper.vueloToDTO( entity );
		if ( entity.getIdReserva().isPresent() ) {
			vuelo.add( linkReserva( entity.getIdReserva().orElseThrow() ) );
		}
		return vuelo;
	}

	private Link linkReserva( UUID idReserva ) {
		return Link.of( linkService.privateApiV2().path( Constants.RESOURCE_RESERVAS_VUELO ).pathSegment( idReserva.toString() ).build().toString(), "reserva-vuelo" );
	}

}
