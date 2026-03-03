package es.um.atica.umufly.vuelos.application.usecase.obtenerreserva;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public class ObtenerReservaQuery extends Query<ReservaVuelo> {

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
