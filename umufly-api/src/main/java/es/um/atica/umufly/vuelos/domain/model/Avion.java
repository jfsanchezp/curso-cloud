package es.um.atica.umufly.vuelos.domain.model;

public record Avion( int capacidad ) {

	public Avion {
		if ( capacidad <= 0 ) {
			throw new IllegalArgumentException( "La capacidad del avión no puede ser inferior o igual a 0" );
		}
	}

}
