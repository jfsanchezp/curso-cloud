package es.um.atica.umufly.vuelos.domain.model;

import java.time.LocalDateTime;

public record Itinerario( LocalDateTime salida, LocalDateTime llegada, String origen, String destino ) {

	public Itinerario {
		if ( salida == null || llegada == null ) {
			throw new IllegalArgumentException( "Es obligatorio indicar la salida y llegada en el itinerario" );
		}

		if ( origen == null || origen.isBlank() || destino == null || destino.isBlank() ) {
			throw new IllegalArgumentException( "Es obligatorio indicar el origen y destino en el itinerario" );
		}

		if ( salida.isAfter( llegada ) ) {
			throw new IllegalAccessError( "La salida no puede ser posterior a la llegada" );
		}

		if ( origen.equals( destino ) ) {
			throw new IllegalAccessError( "El origen no puede ser igual al destino" );
		}

	}

}
