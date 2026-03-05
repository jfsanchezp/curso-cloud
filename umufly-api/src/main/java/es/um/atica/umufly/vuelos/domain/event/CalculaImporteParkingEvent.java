package es.um.atica.umufly.vuelos.domain.event;


import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.events.Event;

public class CalculaImporteParkingEvent extends Event {
	
	private final String idParking;
	private final boolean tieneReserva;
	
	private CalculaImporteParkingEvent(String idParking,boolean tieneReserva) {
		this.idParking = idParking;
		this.tieneReserva = tieneReserva;
	}
	
	public static CalculaImporteParkingEvent of (UUID idParking, boolean tieneReserva) {
		return new CalculaImporteParkingEvent(idParking.toString(), tieneReserva);
	}

	public String getIdParking() {
		return idParking;
	}

	public boolean tieneReserva() {
		return tieneReserva;
	}
}
