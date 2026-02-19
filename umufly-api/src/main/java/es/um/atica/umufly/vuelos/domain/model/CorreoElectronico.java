package es.um.atica.umufly.vuelos.domain.model;

import java.util.regex.Pattern;

public record CorreoElectronico( String valor ) {

	private static final String PATRON_EMAIL = "^[a-zA-Z0-9._\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

	public CorreoElectronico {
		if ( valor == null || valor.isBlank() ) {
			throw new IllegalArgumentException( "El correo electrónico no puede ser nulo" );
		}
		if ( !Pattern.matches( PATRON_EMAIL, valor ) ) {
			throw new IllegalArgumentException( "El correo electrónico no tiene un formato válido" );
		}
	}
}
