package es.um.atica.umufly.vuelos.adaptors.consumers;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.um.atica.umufly.vuelos.application.port.ReservasVueloWriteRepository;
import es.um.atica.umufly.vuelos.domain.event.CancelarReservaVueloOKEvent;

@Component
public class CancelarReservaVueloOKConsumer {

	private final ReservasVueloWriteRepository reservasVueloWriteRepository;

	public CancelarReservaVueloOKConsumer( ReservasVueloWriteRepository reservasVueloWriteRepository) {
		this.reservasVueloWriteRepository = reservasVueloWriteRepository;

	}
	
	@EventListener
	@Transactional
	public void accept(CancelarReservaVueloOKEvent event)
	{
		final UUID id = UUID.fromString((String)event.getMetaData().get("id"));
		reservasVueloWriteRepository.cancelReserva( id );
	}
}
