package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import java.util.UUID;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper.ApiRestV2Mapper;
import es.um.atica.umufly.vuelos.application.usecase.reservas.GestionarReservaUseCase;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import jakarta.validation.Valid;

@RestController
public class ReservasEndpointV2 {

	private final GestionarReservaUseCase gestionarReservaUseCase;
	private final ReservasModelAssemblerV2 reservasModelAssembler;
	private final PagedResourcesAssembler<ReservaVuelo> pagedResourcesAssembler;
	private final AuthService authService;

	public ReservasEndpointV2( GestionarReservaUseCase gestionarReservaUseCase, ReservasModelAssemblerV2 reservasModelAssembler, PagedResourcesAssembler<ReservaVuelo> pagedResourcesAssembler, AuthService authService ) {
		this.gestionarReservaUseCase = gestionarReservaUseCase;
		this.reservasModelAssembler = reservasModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@PostMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO )
	public ReservaVueloDTO creaReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestBody @Valid ReservaVueloDTO nuevaReservaVuelo ) {
		return reservasModelAssembler.toModel( gestionarReservaUseCase.creaReserva( authService.parseUserHeader( usuario ), nuevaReservaVuelo.getVuelo().getId(), ClaseAsientoReserva.valueOf( nuevaReservaVuelo.getClaseAsiento().toString() ),
				ApiRestV2Mapper.pasajeroToModel( nuevaReservaVuelo.getPasajero() ) ) );
	}

	@DeleteMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO + Constants.ID_RESERVA )
	public ReservaVueloDTO cancelarReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idReserva" ) UUID idReserva ) {
		return reservasModelAssembler.toModel( gestionarReservaUseCase.cancelarReserva( authService.parseUserHeader( usuario ), idReserva ) );
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO )
	public CollectionModel<ReservaVueloDTO> getReservas( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size ) {
		return pagedResourcesAssembler.toModel( gestionarReservaUseCase.listarReservas( authService.parseUserHeader( usuario ), page, size ), reservasModelAssembler );
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO + Constants.ID_RESERVA )
	public ReservaVueloDTO getReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idReserva" ) UUID idReserva ) {
		return reservasModelAssembler.toModel( gestionarReservaUseCase.obtenerReserva( authService.parseUserHeader( usuario ), idReserva ) );
	}

}
