package es.um.atica.umufly.vuelos.application.usecase.cancelarreservas;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.CommandHandler;
import es.um.atica.fundewebjs.umubus.domain.events.EventBus;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class CancelarReservaCommandHandler implements CommandHandler<CancelarReservaCommand> {

	private final ReservasVueloReadRepository reservasVueloReadRepository;
	private final EventBus eventBus;
	private final Clock clock;

	public CancelarReservaCommandHandler( ReservasVueloReadRepository reservasVueloRepository, EventBus eventBus, Clock clock ) {
		this.reservasVueloReadRepository = reservasVueloRepository;
		this.eventBus = eventBus;
		this.clock = clock;
	}

	@Override
	public void handle( CancelarReservaCommand command ){
		
		// 1. Recuperamos la reserva
		ReservaVuelo reservaVuelo = reservasVueloReadRepository.findReservaById( command.getDocumentoIdentidadTitular(), command.getIdReserva() );
		try {
			
			UUID idReservaFormalizada = reservasVueloReadRepository.findIdFormalizadaByReservaById(command.getIdReserva());
			
			if (idReservaFormalizada == null) throw new NoSuchElementException("La reserva indicada no ha sido solicitada a traves de umufly, pongase en contacto con MUCHO VUELO");
			reservaVuelo.cancelarReserva( idReservaFormalizada, LocalDateTime.now( clock ) );
		} catch (Exception e) {
			reservaVuelo.cancelarReservaKO(e.getMessage());
		}
		// 3. Cancelamos la reserva llamando al backoffice para que se haga eco de la cancelacion
		//Ya no nos encargamos nosotros, publicamos el evento y delegamos
		eventBus.publish(reservaVuelo);
	}
	
}
