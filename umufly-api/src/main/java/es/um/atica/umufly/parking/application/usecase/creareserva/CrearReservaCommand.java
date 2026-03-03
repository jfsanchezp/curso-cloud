package es.um.atica.umufly.parking.application.usecase.creareserva;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommand;
import es.um.atica.umufly.parking.domain.model.Periodo;
import es.um.atica.umufly.parking.domain.model.ReservaParking;
import es.um.atica.umufly.parking.domain.model.TipoEstacionamiento;
import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;

public class CrearReservaCommand extends SyncCommand<ReservaParking> {

	private DocumentoIdentidad documentoIdentidadPasajero;
	private UUID idParking;
	private TipoEstacionamiento tipoEstacionamiento;
	private Periodo periodoEstacionamiento;

	private CrearReservaCommand( DocumentoIdentidad documentoIdentidadPasajero, UUID idParking, TipoEstacionamiento tipoEstacionamiento, Periodo periodoEstacionamiento ) {
		this.documentoIdentidadPasajero = documentoIdentidadPasajero;
		this.idParking = idParking;
		this.tipoEstacionamiento = tipoEstacionamiento;
		this.periodoEstacionamiento = periodoEstacionamiento;
	}

	public static CrearReservaCommand of( DocumentoIdentidad documentoIdentidadPasajero, UUID idParking, TipoEstacionamiento tipoEstacionamiento, Periodo periodoEstacionamiento ) {
		return new CrearReservaCommand( documentoIdentidadPasajero, idParking, tipoEstacionamiento, periodoEstacionamiento );
	}

	public DocumentoIdentidad getDocumentoIdentidadPasajero() {
		return documentoIdentidadPasajero;
	}

	public UUID getIdParking() {
		return idParking;
	}

	public TipoEstacionamiento getTipoEstacionamiento() {
		return tipoEstacionamiento;
	}

	public Periodo getPeriodoEstacionamiento() {
		return periodoEstacionamiento;
	}


}
