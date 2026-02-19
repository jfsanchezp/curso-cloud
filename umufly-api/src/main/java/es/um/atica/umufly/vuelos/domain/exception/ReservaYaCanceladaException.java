package es.um.atica.umufly.vuelos.domain.exception;

public class ReservaYaCanceladaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReservaYaCanceladaException( String message ) {
		super( message );
	}

}
