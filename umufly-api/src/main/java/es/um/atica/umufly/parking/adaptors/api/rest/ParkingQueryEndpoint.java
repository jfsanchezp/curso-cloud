package es.um.atica.umufly.parking.adaptors.api.rest;

import java.util.UUID;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.parking.adaptors.api.rest.dto.ReservaParkingDTO;
import es.um.atica.umufly.parking.application.usecase.listarparking.ListaParkingsQuery;
import es.um.atica.umufly.parking.application.usecase.listarparking.ListaParkingsQueryHandler;
import es.um.atica.umufly.parking.application.usecase.obtenerparking.ObtenerParkingQuery;
import es.um.atica.umufly.parking.application.usecase.obtenerparking.ObtenerParkingQueryHandler;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@RestController
public class ParkingQueryEndpoint {

	private final ListaParkingsQueryHandler listaParkingsQueryHandler;
	private final ObtenerParkingQueryHandler obtenerParkingQueryHandler;
	private final ParkingModelAssembler reservasModelAssembler;
	private final PagedResourcesAssembler<ReservaParking> pagedResourcesAssembler;
	private final AuthServiceParking authService;

	public ParkingQueryEndpoint( ListaParkingsQueryHandler listaParkingsQueryHandler, ObtenerParkingQueryHandler obtenerParkingQueryHandler, ParkingModelAssembler reservasModelAssembler, PagedResourcesAssembler<ReservaParking> pagedResourcesAssembler,
			AuthServiceParking authService ) {
		this.listaParkingsQueryHandler = listaParkingsQueryHandler;
		this.obtenerParkingQueryHandler = obtenerParkingQueryHandler;
		this.reservasModelAssembler = reservasModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_1 + Constants.RESOURCE_PARKINGS )
	public CollectionModel<ReservaParkingDTO> getReservas( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size )
			throws Exception {
		return pagedResourcesAssembler.toModel( listaParkingsQueryHandler.handle( ListaParkingsQuery.of( authService.parseUserHeader( usuario ), page, size ) ), reservasModelAssembler );
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_1 + Constants.RESOURCE_PARKINGS + Constants.ID_RESERVA )
	public ReservaParkingDTO getReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idParking" ) UUID idParking ) throws Exception {
		return reservasModelAssembler.toModel( obtenerParkingQueryHandler.handle( ObtenerParkingQuery.of( authService.parseUserHeader( usuario ), idParking ) ) );
	}

}
