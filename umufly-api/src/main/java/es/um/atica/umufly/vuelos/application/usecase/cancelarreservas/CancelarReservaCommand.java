package es.um.atica.umufly.vuelos.application.usecase.cancelarreservas;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Command;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

public class CancelarReservaCommand extends Command {

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
