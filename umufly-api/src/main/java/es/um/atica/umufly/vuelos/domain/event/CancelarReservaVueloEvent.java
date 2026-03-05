package es.um.atica.umufly.vuelos.domain.event;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.events.Event;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

public class CancelarReservaVueloEvent extends Event {
	
	private final String idReserva;
	private final String idReservaFormalizada;
	private final String tipoIdentificadorTitular;
	private final String numeroIdentificadorTitular;
	
	private CancelarReservaVueloEvent(String idReserva, String idReservaFormalizada, String tipoIdentificadorTitular, String numeroIdentificadorTitular) {
		this.tipoIdentificadorTitular = tipoIdentificadorTitular;
		this.numeroIdentificadorTitular = numeroIdentificadorTitular;
		this.idReserva = idReserva;
		this.idReservaFormalizada = idReservaFormalizada;
	}
	
	public static CancelarReservaVueloEvent of(UUID idReserva, UUID idReservaFormalizada, DocumentoIdentidad documentoIdentidadTitular) {
		return new CancelarReservaVueloEvent(idReserva.toString(), idReservaFormalizada.toString(), documentoIdentidadTitular.tipo().toString(), documentoIdentidadTitular.identificador());
	}

	public String getIdReserva() {
		return idReserva;
	}
	
	public String getIdReservaFormalizada() {
		return idReservaFormalizada;
	}

	public String getTipoIdentificadorTitular() {
		return tipoIdentificadorTitular;
	}

	public String getNumeroIdentificadorTitular() {
		return numeroIdentificadorTitular;
	}
	
}
