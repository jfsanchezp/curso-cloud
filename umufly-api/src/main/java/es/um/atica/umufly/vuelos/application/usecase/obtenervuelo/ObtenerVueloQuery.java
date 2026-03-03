package es.um.atica.umufly.vuelos.application.usecase.obtenervuelo;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;

public class ObtenerVueloQuery extends Query<VueloAmpliadoDTO> {

	private DocumentoIdentidad documentoIdentidad;
	private UUID idVuelo;

	private ObtenerVueloQuery( DocumentoIdentidad documentoIdentidad, UUID idVuelo ) {
		this.documentoIdentidad = documentoIdentidad;
		this.idVuelo = idVuelo;
	}

	public static ObtenerVueloQuery of( DocumentoIdentidad documentoIdentidad, UUID idVuelo ) {
		return new ObtenerVueloQuery( documentoIdentidad, idVuelo );
	}


	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public UUID getIdVuelo() {
		return idVuelo;
	}


}
