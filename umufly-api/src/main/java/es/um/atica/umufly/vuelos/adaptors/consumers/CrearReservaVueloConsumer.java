package es.um.atica.umufly.vuelos.adaptors.consumers;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.events.EventBus;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloWritePort;
import es.um.atica.umufly.vuelos.application.port.VuelosReadRepository;
import es.um.atica.umufly.vuelos.domain.event.CrearReservaVueloEvent;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.CorreoElectronico;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.EstadoReserva;
import es.um.atica.umufly.vuelos.domain.model.Nacionalidad;
import es.um.atica.umufly.vuelos.domain.model.NombreCompleto;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.event.EventListener;

@Component
public class CrearReservaVueloConsumer {

	private final VuelosReadRepository vuelosRepository;
	private final ReservasVueloWritePort formalizacionReservasVueloPort;
	private final EventBus eventBus;
	public CrearReservaVueloConsumer( VuelosReadRepository vuelosRepository, ReservasVueloWritePort formalizacionReservasVueloPort, EventBus eventBus) {
		this.vuelosRepository = vuelosRepository;
		this.formalizacionReservasVueloPort = formalizacionReservasVueloPort;
		this.eventBus = eventBus;
	}
	
	@EventListener
	public void accept(CrearReservaVueloEvent event)
	{
		final UUID id = UUID.fromString((String)event.getMetaData().get("id"));
		final DocumentoIdentidad identificadorTitular = new DocumentoIdentidad(TipoDocumento.valueOf((String)event.getMetaData().get("tipoIdentificadorTitular")), (String)event.getMetaData().get("numeroIdentificadorTitular"));
		final Pasajero pasajero = Pasajero.of(new DocumentoIdentidad(TipoDocumento.valueOf((String)event.getMetaData().get("tipoIdentificadorPasajero")), (String)event.getMetaData().get("numeroIdentificadorPasajero")),
				new NombreCompleto((String)event.getMetaData().get("nombrePasajero"), (String)event.getMetaData().get("primerApellidoPasajero"), (String)event.getMetaData().get("segundoApellidoPasajero")),
				new CorreoElectronico((String)event.getMetaData().get("correoPasajero")),new Nacionalidad((String)event.getMetaData().get("nacionalidadPasajero")));
		final Vuelo vuelo = vuelosRepository.findVuelo( UUID.fromString((String)event.getMetaData().get("idVuelo") ));
		final ClaseAsientoReserva clase = ClaseAsientoReserva.valueOf((String)event.getMetaData().get("clase"));
		final EstadoReserva estado = EstadoReserva.valueOf((String)event.getMetaData().get("estado"));
		final LocalDateTime fechaReserva = (LocalDateTime)event.getMetaData().get("fechaReserva");
		
		ReservaVuelo reserva = ReservaVuelo.of(id, identificadorTitular, pasajero, vuelo, clase, fechaReserva, estado);
		
		try {
			UUID idReservaFormalizada = formalizacionReservasVueloPort.formalizarReservaVuelo( reserva );
			reserva.crearReservaOK(idReservaFormalizada);
		} catch (Exception exception) {
			reserva.crearReservaKO(exception.getMessage());
		}
		eventBus.publish(reserva);
	}
}
