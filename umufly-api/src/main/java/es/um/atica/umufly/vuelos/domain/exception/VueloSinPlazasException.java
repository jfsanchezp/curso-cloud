package es.um.atica.umufly.vuelos.domain.exception;

public class VueloSinPlazasException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VueloSinPlazasException( String message ) {
		super( message );
	}

}
