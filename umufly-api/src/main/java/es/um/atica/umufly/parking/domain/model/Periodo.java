package es.um.atica.umufly.parking.domain.model;

import java.time.LocalDateTime;

public record Periodo( LocalDateTime inicio, LocalDateTime fin ) {

	public Periodo {
		if ( inicio == null || fin == null ) {
			throw new IllegalArgumentException( "Es obligatorio indicar el inicio y fin de la estancia" );
		}

		if ( inicio.isAfter( fin ) ) {
			throw new IllegalAccessError( "La salida no puede ser posterior a la llegada" );
		}
	}
}
