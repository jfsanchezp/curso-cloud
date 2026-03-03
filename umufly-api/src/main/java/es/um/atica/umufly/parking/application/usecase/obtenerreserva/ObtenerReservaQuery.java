package es.um.atica.umufly.parking.application.usecase.obtenerreserva;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.parking.domain.model.ReservaParking;
import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;

public class ObtenerReservaQuery extends Query<ReservaParking> {

	private DocumentoIdentidad documentoIdentidad;
	private UUID idReserva;

	private ObtenerReservaQuery( DocumentoIdentidad documentoIdentidad, UUID idReserva ) {
		this.documentoIdentidad = documentoIdentidad;
		this.idReserva = idReserva;
	}

	public static ObtenerReservaQuery of( DocumentoIdentidad documentoIdentidad, UUID idReserva ) {
		return new ObtenerReservaQuery( documentoIdentidad, idReserva );
	}


	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public UUID getIdReserva() {
		return idReserva;
	}


}
