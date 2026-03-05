package es.um.atica.umufly.vuelos.adaptors.consumers;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloWriteRepository;
import es.um.atica.umufly.vuelos.domain.event.CrearReservaVueloOKEvent;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;

@Component
public class CrearReservaVueloOKConsumer {

	private final ReservasVueloWriteRepository reservasVueloWriteRepository;
	private final ReservasVueloReadRepository reservasVueloReadRepository;

	public CrearReservaVueloOKConsumer( ReservasVueloWriteRepository reservasVueloWriteRepository, ReservasVueloReadRepository reservasVueloReadRepository) {
		this.reservasVueloWriteRepository = reservasVueloWriteRepository;
		this.reservasVueloReadRepository = reservasVueloReadRepository;

	}
	
	@EventListener
	@Transactional
	public void accept(CrearReservaVueloOKEvent event)
	{
		final UUID id = UUID.fromString((String)event.getMetaData().get("id"));
		final DocumentoIdentidad identificadorTitular = new DocumentoIdentidad(TipoDocumento.valueOf((String)event.getMetaData().get("tipoIdentificadorTitular")), (String)event.getMetaData().get("numeroIdentificadorTitular"));
		final UUID idReservaFormalizada = UUID.fromString((String)event.getMetaData().get("idReservaFormalizada"));
		
		ReservaVuelo reserva = reservasVueloReadRepository.findReservaById( identificadorTitular, id );
		
		reserva.formalizarReserva();
		reservasVueloWriteRepository.persistirFormalizacionReserva( id, idReservaFormalizada );
	}
}
