package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoDocumentoDTO {

	NIF( "N" ), NIE( "E" ), PASAPORTE( "P" );


	private final String codigo;

	TipoDocumentoDTO( String codigo ) {
		this.codigo = codigo;
	}

	@JsonValue
	public String getCodigo() {
		return codigo;
	}

}
