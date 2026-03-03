package es.um.atica.umufly.vuelos.application.usecase.cancelarreservas;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommand;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public class CancelarReservaCommand extends SyncCommand<ReservaVuelo> {

	private final DocumentoIdentidad documentoIdentidadTitular;
	private final UUID idReserva;

	private CancelarReservaCommand( DocumentoIdentidad documentoIdentidadTitular, UUID idReserva ) {
		this.documentoIdentidadTitular = documentoIdentidadTitular;
		this.idReserva = idReserva;
	}

	public static CancelarReservaCommand of( DocumentoIdentidad documentoIdentidadTitular, UUID idReserva ) {
		return new CancelarReservaCommand( documentoIdentidadTitular, idReserva );
	}

	public DocumentoIdentidad getDocumentoIdentidadTitular() {
		return documentoIdentidadTitular;
	}

	public UUID getIdReserva() {
		return idReserva;
	}
}
