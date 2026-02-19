package es.um.atica.umufly.vuelos.domain.exception;

public class VueloNoReservableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VueloNoReservableException( String message ) {
		super( message );
	}

}
