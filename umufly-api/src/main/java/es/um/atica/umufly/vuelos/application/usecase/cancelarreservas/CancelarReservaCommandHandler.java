package es.um.atica.umufly.vuelos.application.usecase.cancelarreservas;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommandHandler;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloWritePort;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloWriteRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class CancelarReservaCommandHandler implements SyncCommandHandler<ReservaVuelo, CancelarReservaCommand> {

	private final ReservasVueloReadRepository reservasVueloReadRepository;
	private final ReservasVueloWriteRepository reservasVueloWriteRepository;
	private final ReservasVueloWritePort formalizacionReservasVueloPort;
	private final Clock clock;

	public CancelarReservaCommandHandler( ReservasVueloReadRepository reservasVueloRepository, ReservasVueloWriteRepository reservasVueloWriteRepository, ReservasVueloWritePort formalizacionReservasVueloPort, Clock clock ) {
		this.reservasVueloReadRepository = reservasVueloRepository;
		this.reservasVueloWriteRepository = reservasVueloWriteRepository;
		this.formalizacionReservasVueloPort = formalizacionReservasVueloPort;
		this.clock = clock;
	}

	@Override
	public ReservaVuelo handle( CancelarReservaCommand command ) throws Exception {
		// 1. Recuperamos la reserva
		ReservaVuelo reservaVuelo = reservasVueloReadRepository.findReservaById( command.getDocumentoIdentidadTitular(), command.getIdReserva() );

		reservaVuelo.cancelarReserva( LocalDateTime.now( clock ) );
		UUID idReservaFormalizada = reservasVueloReadRepository.findIdFormalizadaByReservaById(command.getIdReserva());

		// 2. Cancelamos la reserva llamando al backoffice para que se haga eco de la cancelacion
		if ( idReservaFormalizada == null ) {
			throw new NoSuchElementException("La reserva indicada no ha sido solicitada a traves de umufly, pongase en contacto con MUCHO VUELO");
		}
		formalizacionReservasVueloPort.cancelarReservaVuelo( command.getDocumentoIdentidadTitular(), idReservaFormalizada );

		// 3. Cancelamos la reserva en el fronOffice
		reservasVueloWriteRepository.cancelReserva( reservaVuelo.getId() );

		return reservaVuelo;
	}

}
