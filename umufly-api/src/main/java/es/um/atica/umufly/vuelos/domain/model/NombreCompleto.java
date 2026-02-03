package es.um.atica.umufly.vuelos.domain.model;

public record NombreCompleto( String nombre, String primerApellido, String segundoApellido ) {

	public NombreCompleto {
		if ( nombre == null || nombre.isBlank() ) {
			throw new IllegalArgumentException( "Es obligatorio indicar el nombre" );
		}
		if ( primerApellido == null || primerApellido.isBlank() ) {
			throw new IllegalArgumentException( "Es obligatorio indicar el primer apellido" );
		}
	}
}
