package es.um.atica.umufly.vuelos.adaptors.consumers;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.um.atica.umufly.vuelos.application.port.ReservasVueloWriteRepository;
import es.um.atica.umufly.vuelos.domain.event.CrearReservaVueloKOEvent;

@Component
public class CrearReservaVueloKOConsumer {

	private final ReservasVueloWriteRepository reservasVueloWriteRepository;

	public CrearReservaVueloKOConsumer( ReservasVueloWriteRepository reservasVueloWriteRepository) {
		this.reservasVueloWriteRepository = reservasVueloWriteRepository;

	}
	
	@EventListener
	@Transactional
	public void accept(CrearReservaVueloKOEvent event)
	{
		final UUID id = UUID.fromString((String)event.getMetaData().get("id"));
		final String mensajeError = (String)event.getMetaData().get("mensajeError");
		
		reservasVueloWriteRepository.errorReserva( id, mensajeError );
		reservasVueloWriteRepository.cancelReserva(id);
	}
}
