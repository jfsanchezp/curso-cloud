package es.um.atica.umufly.parking.adaptors.api.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.parking.adaptors.api.rest.dto.ReservaParkingDTO;
import es.um.atica.umufly.parking.application.usecase.cancelarparking.CancelarParkingCommand;
import es.um.atica.umufly.parking.application.usecase.cancelarparking.CancelarParkingCommandHandler;
import es.um.atica.umufly.parking.application.usecase.crearparking.CrearParkingCommand;
import es.um.atica.umufly.parking.application.usecase.crearparking.CrearParkingCommandHandler;
import es.um.atica.umufly.parking.domain.model.Periodo;

@RestController
public class ParkingCommandEndpoint {

	private final CrearParkingCommandHandler crearParkingCommandHandler;
	private final CancelarParkingCommandHandler cancelarParkingCommandHandler;
	private final ParkingModelAssembler reservasModelAssembler;
	private final AuthServiceParking authService;

	public ParkingCommandEndpoint( CrearParkingCommandHandler crearParkingCommandHandler, CancelarParkingCommandHandler cancelarParkingCommandHandler, ParkingModelAssembler reservasModelAssembler,
			AuthServiceParking authService ) {
		this.crearParkingCommandHandler = crearParkingCommandHandler;
		this.cancelarParkingCommandHandler = cancelarParkingCommandHandler;
		this.reservasModelAssembler = reservasModelAssembler;
		this.authService = authService;
	}

	@PostMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_1 + Constants.RESOURCE_PARKINGS )
	public ReservaParkingDTO creaParking( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestBody @Valid ReservaParkingDTO nuevaReserva ) throws Exception {
		return reservasModelAssembler.toModel( crearParkingCommandHandler
				.handle( CrearParkingCommand.of( authService.parseUserHeader( usuario ), new Periodo( nuevaReserva.getFechaInicio(), nuevaReserva.getFechaFin() ) ) ) );
	}

	@DeleteMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_1 + Constants.RESOURCE_PARKINGS + Constants.ID_RESERVA )
	public ReservaParkingDTO cancelarParking( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idParking" ) UUID idParking ) throws Exception {
		return reservasModelAssembler.toModel( cancelarParkingCommandHandler.handle( CancelarParkingCommand.of( authService.parseUserHeader( usuario ), idParking ) ) );
	}
}
