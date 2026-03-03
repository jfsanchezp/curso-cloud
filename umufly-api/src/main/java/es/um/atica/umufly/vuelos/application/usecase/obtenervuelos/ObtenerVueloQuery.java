package es.um.atica.umufly.vuelos.application.usecase.obtenervuelos;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;


public class ObtenerVueloQuery extends Query<VueloAmpliadoDTO> {

	private final DocumentoIdentidad documentoIdentidadPasajero;
	private final UUID idVuelo;

	private ObtenerVueloQuery( DocumentoIdentidad documentoIdentidadPasajero, UUID idVuelo ) {
		this.documentoIdentidadPasajero = documentoIdentidadPasajero;
		this.idVuelo = idVuelo;
	}

	public static ObtenerVueloQuery of( DocumentoIdentidad documentoIdentidadPasajero, UUID idVuelo ) {
		return new ObtenerVueloQuery( documentoIdentidadPasajero, idVuelo );
	}

	public DocumentoIdentidad getDocumentoIdentidadPasajero() {
		return documentoIdentidadPasajero;
	}

	public UUID getIdVuelo() {
		return idVuelo;
	}
}
