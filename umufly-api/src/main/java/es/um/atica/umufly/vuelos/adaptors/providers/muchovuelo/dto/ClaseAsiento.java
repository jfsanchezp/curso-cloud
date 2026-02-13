package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClaseAsiento {

	ECONOMICA( "E" ), BUSINESS( "B" ), PRIMERA( "P" );

	private final String codigo;

	ClaseAsiento( String codigo ) {
		this.codigo = codigo;
	}

	@JsonValue
	public String getCodigo() {
		return codigo;
	}
}
