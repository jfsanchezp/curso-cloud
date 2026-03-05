package es.um.atica.umufly.vuelos.domain.event;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.events.Event;

public class CancelarReservaVueloOKEvent extends Event {
	
	private final String id;
	
	private CancelarReservaVueloOKEvent(String id) {
		this.id = id;
	}
	
	public static CancelarReservaVueloOKEvent of (UUID id) {
		return new CancelarReservaVueloOKEvent(id.toString());
	}

	public String getId() {
		return id;
	}
}
