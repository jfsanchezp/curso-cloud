package es.um.atica.umufly.vuelos.domain.event;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.events.Event;

public class CrearReservaVueloKOEvent extends Event {
	
	private final String id;
	private final String mensajeError;
	
	private CrearReservaVueloKOEvent(String id, String mensajeError) {
		this.id = id;
		this.mensajeError = mensajeError;
	}
	
	public static CrearReservaVueloKOEvent of (UUID id, String mensajeError) {
		return new CrearReservaVueloKOEvent(id.toString(), mensajeError);
	}

	public String getId() {
		return id;
	}

	public String getMensajeError() {
		return mensajeError;
	}
	
}
