package es.um.atica.umufly.vuelos.domain.exception;

public class LimiteReservasPorPasajeroEnVueloSuperadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LimiteReservasPorPasajeroEnVueloSuperadoException( String message ) {
		super( message );
	}

}
