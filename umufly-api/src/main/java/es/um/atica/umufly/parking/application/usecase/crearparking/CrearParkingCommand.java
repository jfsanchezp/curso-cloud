package es.um.atica.umufly.parking.application.usecase.crearparking;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommand;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.Periodo;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

public class CrearParkingCommand extends SyncCommand<ReservaParking> {

	private final DocumentoIdentidad documentoIdentidadTitular;
	private final Periodo periodoEstacionamiento;

	private CrearParkingCommand( DocumentoIdentidad documentoIdentidadTitular, Periodo periodoEstacionamiento ) {
		this.documentoIdentidadTitular = documentoIdentidadTitular;
		this.periodoEstacionamiento = periodoEstacionamiento;
	}

	public static CrearParkingCommand of( DocumentoIdentidad documentoIdentidadTitular, Periodo periodoEstacionamiento ) {
		return new CrearParkingCommand( documentoIdentidadTitular, periodoEstacionamiento );
	}

	public DocumentoIdentidad getDocumentoIdentidadTitular() {
		return documentoIdentidadTitular;
	}

	public Periodo getPeriodoEstacionamiento() {
		return periodoEstacionamiento;
	}


}
