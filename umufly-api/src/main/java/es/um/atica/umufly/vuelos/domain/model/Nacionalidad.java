package es.um.atica.umufly.vuelos.domain.model;

public record Nacionalidad( String valor ) {

	public Nacionalidad {
		if ( valor == null || valor.isBlank() ) {
			throw new IllegalArgumentException( "La nacionalidad no puede ser nula" );
		}
	}

}
