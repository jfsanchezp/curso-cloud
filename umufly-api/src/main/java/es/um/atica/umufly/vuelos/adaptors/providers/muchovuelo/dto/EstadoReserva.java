package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoReserva {

	ACTIVA( "A" ), CANCELADA( "C" );

	private final String codigo;

	EstadoReserva( String codigo ) {
		this.codigo = codigo;
	}

	@JsonValue
	public String getCodigo() {
		return codigo;
	}

}
