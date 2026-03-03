package es.um.atica.umufly.parking.application.usecase.cancelarparking;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommand;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

public class CancelarParkingCommand extends SyncCommand<ReservaParking> {

	private final DocumentoIdentidad documentoIdentidadTitular;
	private final UUID idParking;

	private CancelarParkingCommand( DocumentoIdentidad documentoIdentidadTitular, UUID idParking ) {
		this.documentoIdentidadTitular = documentoIdentidadTitular;
		this.idParking = idParking;
	}

	public static CancelarParkingCommand of( DocumentoIdentidad documentoIdentidadTitular, UUID idParking ) {
		return new CancelarParkingCommand( documentoIdentidadTitular, idParking );
	}

	public DocumentoIdentidad getDocumentoIdentidadTitular() {
		return documentoIdentidadTitular;
	}

	public UUID getIdParking() {
		return idParking;
	}
}
