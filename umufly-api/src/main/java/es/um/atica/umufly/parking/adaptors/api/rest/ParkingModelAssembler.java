package es.um.atica.umufly.parking.adaptors.api.rest;

import java.util.UUID;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.parking.adaptors.api.rest.dto.ReservaParkingDTO;
import es.um.atica.umufly.parking.adaptors.api.rest.mapper.ApiRestMapper;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@Component
public class ParkingModelAssembler implements RepresentationModelAssembler<ReservaParking, ReservaParkingDTO> {

	private final LinkServiceParking linkService;

	public ParkingModelAssembler( LinkServiceParking linkService ) {
		this.linkService = linkService;
	}

	@Override
	public ReservaParkingDTO toModel( ReservaParking entity ) {
		ReservaParkingDTO reservaParking = ApiRestMapper.reservaParkingToDTO( entity );
		reservaParking.add( linkSelf( entity.getId() ) );
		return reservaParking;
	}

	private Link linkSelf( UUID idParking ) {
		return Link.of( linkService.privateApi().path( Constants.RESOURCE_PARKINGS ).pathSegment( idParking.toString() ).build().toString() );
	}

}
