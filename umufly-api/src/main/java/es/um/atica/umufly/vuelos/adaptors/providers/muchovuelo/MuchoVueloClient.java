package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.TipoDocumentoDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.exception.MuchoVueloClientException;

@Component
public class MuchoVueloClient {

	private static final String API_VERSION_1 = "/v1.0";
	private static final String API_PRIVATE = "/private";
	private static final String API_RECURSO_RESERVAS_VUELO = "/reservas-vuelo";
	private static final String API_HEADER_USUARIO = "X-Usuario";

	private static final String URI_RESERVAS_VUELO_V1 = API_VERSION_1 + API_PRIVATE + API_RECURSO_RESERVAS_VUELO;

	// Cambiado de RestClient a RestTemplate
	private final RestTemplate restTemplateMuchoVuelo;

	public MuchoVueloClient( @Qualifier( "muchoVueloRestTemplate" ) RestTemplate restTemplateMuchoVuelo ) {
		this.restTemplateMuchoVuelo = restTemplateMuchoVuelo;
	}

	public ReservaVueloDTO creaReservaVuelo( ReservaVueloDTO reservaVuelo ) {
		String headerUsuario = getHeaderUsuario( reservaVuelo.getTipoDocumentoTitular(), reservaVuelo.getNumeroDocumentoTitular() );
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( API_HEADER_USUARIO, headerUsuario );
		HttpEntity<ReservaVueloDTO> request = new HttpEntity<>( reservaVuelo, headers );

		try {
			return restTemplateMuchoVuelo.postForObject( URI_RESERVAS_VUELO_V1, request, ReservaVueloDTO.class );
		} catch ( HttpStatusCodeException ex ) {
			throw new MuchoVueloClientException( "MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(), ex );
		}
	}

	public void cancelarReservaVuelo( TipoDocumentoDTO tipoDocumentoTitular, String numeroDocumentoTitular, UUID idReserva ) {
		String headerUsuario = getHeaderUsuario( tipoDocumentoTitular, numeroDocumentoTitular );
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( API_HEADER_USUARIO, headerUsuario );
		HttpEntity<Void> request = new HttpEntity<>( headers );

		try {
			// RestTemplate usa un mapa o varargs para los parámetros de la URI
			restTemplateMuchoVuelo.exchange( 
					URI_RESERVAS_VUELO_V1 + "/{idReserva}", 
					HttpMethod.DELETE, 
					request, 
					Void.class, 
					idReserva.toString() 
			);
		} catch ( HttpStatusCodeException ex ) {
			throw new MuchoVueloClientException( "MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(), ex );
		}
	}

	private String getHeaderUsuario( TipoDocumentoDTO tipoDocumento, String identificador ) {
		return tipoDocumento.getCodigo() + ":" + identificador;
	}
}