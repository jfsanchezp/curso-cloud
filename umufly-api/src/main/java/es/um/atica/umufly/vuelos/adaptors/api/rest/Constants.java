package es.um.atica.umufly.vuelos.adaptors.api.rest;


public class Constants {

	public static final String PRIVATE_PREFIX = "/private";
	public static final String PUBLIC_PREFIX = "/public";
	public static final String API_VERSION_1 = "/v1.0";
	public static final String RESOURCE_PRUEBAS = "/pruebas";
	public static final String RESOURCE_VUELOS = "/vuelos";
	public static final String ID_VUELOS = "/{idVuelo}";
	public static final String API_VERSION_2 = "/v2.0";

	public static final String PATTERN_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	public static final String RESOURCE_RESERVAS_VUELO = "/reservas-vuelo";

	private Constants() {
		throw new IllegalStateException( "Clase de constantes" );
	}
}
