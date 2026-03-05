package es.um.atica.umufly.vuelos.adaptors.consumers;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.events.EventBus;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloWritePort;
import es.um.atica.umufly.vuelos.domain.event.CancelarReservaVueloEvent;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;

@Component
public class CancelarReservaVueloConsumer {

	private final ReservasVueloReadRepository reservasVueloReadRepository;
	private final ReservasVueloWritePort formalizacionReservasVueloPort;
	private final EventBus eventBus;
	
	public CancelarReservaVueloConsumer( ReservasVueloReadRepository reservasVueloReadRepository, ReservasVueloWritePort formalizacionReservasVueloPort, EventBus eventBus) {
		this.reservasVueloReadRepository = reservasVueloReadRepository;
		this.formalizacionReservasVueloPort = formalizacionReservasVueloPort;
		this.eventBus = eventBus;
	}
	
	@EventListener
	public void accept(CancelarReservaVueloEvent event)
	{
		final UUID idReservaFormalizada = UUID.fromString((String)event.getMetaData().get("idReservaFormalizada"));
		final UUID idReserva = UUID.fromString((String)event.getMetaData().get("idReserva"));
		final DocumentoIdentidad identificadorTitular = new DocumentoIdentidad(TipoDocumento.valueOf((String)event.getMetaData().get("tipoIdentificadorTitular")), (String)event.getMetaData().get("numeroIdentificadorTitular"));
		
		ReservaVuelo reserva = reservasVueloReadRepository.findReservaById( identificadorTitular, idReserva );
		try {
			formalizacionReservasVueloPort.cancelarReservaVuelo( identificadorTitular, idReservaFormalizada );
			reserva.cancelarReservaOK();
		}catch(Exception exception) {
			reserva.cancelarReservaKO(exception.getMessage());
		}
		eventBus.publish(reserva);
	}
}
