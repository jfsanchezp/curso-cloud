package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.exception;


public class MuchoVueloClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MuchoVueloClientException( String message ) {
		super( message );
	}

	public MuchoVueloClientException( String message, Throwable cause ) {
		super( message, cause );
	}

}
