package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import java.util.UUID;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.application.usecase.listarreservas.ListaReservasQuery;
import es.um.atica.umufly.vuelos.application.usecase.listarreservas.ListaReservasQueryHandler;
import es.um.atica.umufly.vuelos.application.usecase.obtenerreservas.ObtenerReservaQuery;
import es.um.atica.umufly.vuelos.application.usecase.obtenerreservas.ObtenerReservaQueryHandler;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@RestController
public class ReservasQueryEndpointV2 {

	private final ListaReservasQueryHandler listaReservasQueryHandler;
	private final ObtenerReservaQueryHandler obtenerReservaQueryHandler;
	private final ReservasModelAssemblerV2 reservasModelAssembler;
	private final PagedResourcesAssembler<ReservaVuelo> pagedResourcesAssembler;
	private final AuthService authService;

	public ReservasQueryEndpointV2( ListaReservasQueryHandler listaReservasQueryHandler, ObtenerReservaQueryHandler obtenerReservaQueryHandler, ReservasModelAssemblerV2 reservasModelAssembler, PagedResourcesAssembler<ReservaVuelo> pagedResourcesAssembler,
			AuthService authService ) {
		this.listaReservasQueryHandler = listaReservasQueryHandler;
		this.obtenerReservaQueryHandler = obtenerReservaQueryHandler;
		this.reservasModelAssembler = reservasModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO )
	public CollectionModel<ReservaVueloDTO> getReservas( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size )
			throws Exception {
		return pagedResourcesAssembler.toModel( listaReservasQueryHandler.handle( ListaReservasQuery.of( authService.parseUserHeader( usuario ), page, size ) ), reservasModelAssembler );
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO + Constants.ID_RESERVA )
	public ReservaVueloDTO getReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idReserva" ) UUID idReserva ) throws Exception {
		return reservasModelAssembler.toModel( obtenerReservaQueryHandler.handle( ObtenerReservaQuery.of( authService.parseUserHeader( usuario ), idReserva ) ) );
	}

}
