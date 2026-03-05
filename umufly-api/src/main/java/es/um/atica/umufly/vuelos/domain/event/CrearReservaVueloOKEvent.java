package es.um.atica.umufly.vuelos.domain.event;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.events.Event;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

public class CrearReservaVueloOKEvent extends Event {
	
	private final String id;
	private final String tipoIdentificadorTitular;
	private final String numeroIdentificadorTitular;
	private final String idReservaFormalizada;
	
	private CrearReservaVueloOKEvent(String id, String idReservaFormalizada, String tipoIdentificadorTitular, String numeroIdentificadorTitular) {
		this.id = id;
		this.tipoIdentificadorTitular = tipoIdentificadorTitular;
		this.numeroIdentificadorTitular = numeroIdentificadorTitular;
		this.idReservaFormalizada =idReservaFormalizada;
	}
	
	public static CrearReservaVueloOKEvent of (UUID id, DocumentoIdentidad identificadorTitular, UUID idReservaFormalizada) {
		return new CrearReservaVueloOKEvent(id.toString(), idReservaFormalizada.toString(), identificadorTitular.tipo().toString(), identificadorTitular.identificador());
	}

	public String getId() {
		return id;
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
