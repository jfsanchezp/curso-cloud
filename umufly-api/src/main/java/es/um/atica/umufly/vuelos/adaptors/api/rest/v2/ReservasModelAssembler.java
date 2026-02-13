package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import java.util.UUID;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.LinkService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper.ReservaVueloMapper;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component( "v2.reservasModelAssembler" )
public class ReservasModelAssembler implements RepresentationModelAssembler<ReservaVuelo, ReservaVueloDTO> {

	private final LinkService linkService;

	public ReservasModelAssembler( LinkService linkService ) {
		this.linkService = linkService;
	}

	@Override
	public ReservaVueloDTO toModel( ReservaVuelo entity ) {
		ReservaVueloDTO reservaVuelo = ReservaVueloMapper.reservaVueloRestDTOFromModel( entity );
		reservaVuelo.add( linkSelf( entity.getId() ) );
		return reservaVuelo;
	}

	private Link linkSelf( UUID idReserva ) {
		return Link.of( linkService.privateApiV2().path( Constants.RESOURCE_RESERVAS_VUELO ).pathSegment( idReserva.toString() ).build().toString() );
	}

}
