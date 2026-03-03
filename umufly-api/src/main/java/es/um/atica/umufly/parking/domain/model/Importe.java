package es.um.atica.umufly.parking.domain.model;

public record Importe( Integer valor ) {

	public Importe {
		if ( valor == null ) {
			throw new IllegalArgumentException( "Es obligatorio indicar el valor del importe" );
		}

		if ( valor.intValue() <= 0 ) {
			throw new IllegalAccessError( "El importe debe de ser mayor que 0" );
		}

	}

}
