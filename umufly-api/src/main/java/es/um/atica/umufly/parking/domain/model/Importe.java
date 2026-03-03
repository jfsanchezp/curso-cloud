package es.um.atica.umufly.parking.domain.model;

public record Importe( Double valor ) {

	public Importe {
		if ( valor == null ) {
			throw new IllegalArgumentException( "El importe no puede ser nulo" );
		}

		if ( valor <= 0 ) {
			throw new IllegalAccessError( "El importe no puede ser cero o menor" );
		}
	}
}
