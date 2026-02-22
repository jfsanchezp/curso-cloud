package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper.ApiRestV2Mapper;
import es.um.atica.umufly.vuelos.application.usecase.reservas.GestionarReservaUseCase;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import jakarta.validation.Valid;

@RestController
public class ReservasEndpointV2 {

	private final GestionarReservaUseCase gestionarReservaUseCase;
	private final ReservasModelAssemblerV2 reservasModelAssembler;
	private final AuthService authService;

	public ReservasEndpointV2( GestionarReservaUseCase gestionarReservaUseCase, ReservasModelAssemblerV2 reservasModelAssembler, AuthService authService ) {
		this.gestionarReservaUseCase = gestionarReservaUseCase;
		this.reservasModelAssembler = reservasModelAssembler;
		this.authService = authService;
	}

	@PostMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO )
	public ReservaVueloDTO creaReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestBody @Valid ReservaVueloDTO nuevaReservaVuelo ) {
		return reservasModelAssembler.toModel( gestionarReservaUseCase.creaReserva( authService.parseUserHeader( usuario ), nuevaReservaVuelo.getVuelo().getId(), ClaseAsientoReserva.valueOf( nuevaReservaVuelo.getClaseAsiento().toString() ),
				ApiRestV2Mapper.pasajeroToModel( nuevaReservaVuelo.getPasajero() ) ) );
	}

}
