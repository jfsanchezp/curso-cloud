package es.um.atica.umufly.parking.adaptors.api.rest;

public class Constants {

	public static final String PRIVATE_PREFIX = "/private";
	public static final String PUBLIC_PREFIX = "/public";
	public static final String API_VERSION_1 = "/v1.0";
	public static final String RESOURCE_PARKINGS = "/parkings";
	public static final String ID_PARKING = "/{idParking}";

	public static final String PATTERN_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	public static final String ID_RESERVA = "/{idParking}";

	private Constants() {
		throw new IllegalStateException( "Clase de constantes" );
	}
}
