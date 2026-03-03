package es.um.atica.umufly.parking.domain.model;

public record Estacionamiento( TipoEstacionamiento tipo, Double valor ) {

	public Estacionamiento {
		if ( tipo == null ) {
			throw new IllegalArgumentException( "El tipo de estacionamiento no es correcto" );
		}
		if ( valor == null ) {
			throw new IllegalArgumentException( "El importe no puede ser nulo" );
		}

		if ( valor <= 0 ) {
			throw new IllegalAccessError( "El importe no puede ser cero o menor" );
		}
	}
}
