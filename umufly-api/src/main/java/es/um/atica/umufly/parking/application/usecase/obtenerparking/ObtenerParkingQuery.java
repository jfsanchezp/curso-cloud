package es.um.atica.umufly.parking.application.usecase.obtenerparking;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

public class ObtenerParkingQuery extends Query<ReservaParking> {

	private final DocumentoIdentidad documentoIdentidad;
	private final UUID idParking;

	private ObtenerParkingQuery( DocumentoIdentidad documentoIdentidad, UUID idParking ) {
		this.documentoIdentidad = documentoIdentidad;
		this.idParking = idParking;
	}

	public static ObtenerParkingQuery of( DocumentoIdentidad documentoIdentidad, UUID idParking ) {
		return new ObtenerParkingQuery( documentoIdentidad, idParking );
	}

	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public UUID getIdParking() {
		return idParking;
	}
}
