package es.um.atica.umufly.parking.application.usecase.crearparking;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommandHandler;
import es.um.atica.umufly.parking.application.port.ReservasParkingWritePort;
import es.um.atica.umufly.parking.application.port.ReservasParkingWriteRepository;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@Component
public class CrearParkingCommandHandler implements SyncCommandHandler<ReservaParking, CrearParkingCommand> {

	private final ReservasParkingWriteRepository reservasParkingWriteRepository;
	private final ReservasParkingWritePort reservasParkingWritePort;
	private final Clock clock;

	public CrearParkingCommandHandler( ReservasParkingWriteRepository reservasParkingWriteRepository, ReservasParkingWritePort reservasParkingWritePort,
			Clock clock ) {
		this.reservasParkingWriteRepository = reservasParkingWriteRepository;
		this.reservasParkingWritePort = reservasParkingWritePort;
		this.clock = clock;
	}

	@Override
	public ReservaParking handle( CrearParkingCommand command ) throws Exception {
		// 1. Creamos y persistimos la reserva
		ReservaParking reserva = ReservaParking.solicitarParking( command.getDocumentoIdentidadTitular(), command.getPeriodoEstacionamiento(), LocalDateTime.now( clock ) );
		reservasParkingWriteRepository.persistirParking( reserva );

		// Lo dejamos listo para cuando metamos eventos
		reserva.crearEstacionamiento( command.getPeriodoEstacionamiento() );
		reserva.calcularImporte( command.getDocumentoIdentidadTitular(), reserva.getEstacionamiento(), command.getPeriodoEstacionamiento() );
		reservasParkingWriteRepository.actualizarImporteParking( reserva.getId(), reserva.getEstacionamiento(), reserva.getImporte() );

		// 2. Formalizamos la reserva llamando al backoffice para que se haga eco de la nueva reserva que acabamos de crear
		UUID idParkingFormalizada = reservasParkingWritePort.formalizarParking( reserva );
		reserva.formalizarParking();
		reservasParkingWriteRepository.persistirFormalizacionParking( reserva.getId(), idParkingFormalizada );

		return reserva;
	}

}
