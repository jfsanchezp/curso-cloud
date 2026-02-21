package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper.ReservaVueloMapper;
import es.um.atica.umufly.vuelos.application.usecase.reservas.GestionarReservaUseCase;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import jakarta.validation.Valid;

@RestController( "v2.reservasEndpoint" )
public class ReservasEndpoint {

	private final GestionarReservaUseCase creaReservaUseCase;
	private final ReservasModelAssembler reservasModelAssembler;
	private final AuthService authService;

	public ReservasEndpoint( GestionarReservaUseCase creaReservaUseCase, ReservasModelAssembler reservasModelAssembler, AuthService authService ) {
		this.creaReservaUseCase = creaReservaUseCase;
		this.reservasModelAssembler = reservasModelAssembler;
		this.authService = authService;
	}

	@PostMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_RESERVAS_VUELO )
	public ReservaVueloDTO creaReserva( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestBody @Valid ReservaVueloDTO nuevaReservaVuelo ) {
		return reservasModelAssembler.toModel( creaReservaUseCase.creaReservaVuelo( authService.parseUserHeader( usuario ), nuevaReservaVuelo.getVuelo().getId(), ClaseAsientoReserva.valueOf( nuevaReservaVuelo.getClaseAsiento().toString() ),
				ReservaVueloMapper.pasajeroDTOToModel( nuevaReservaVuelo.getPasajero() ) ) );
	}

}
