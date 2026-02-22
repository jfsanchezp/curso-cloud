package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper.ApiRestV2Mapper;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.TipoDocumentoDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.exception.MuchoVueloClientException;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

@Component
public class MuchoVueloClient {

	private static final String API_VERSION_1 = "/v1.0";
	private static final String API_PRIVATE = "/private";
	private static final String API_RECURSO_RESERVAS_VUELO = "/reservas-vuelo";
	private static final String API_HEADER_USUARIO = "X-Usuario";

	private static final String URI_RESERVAS_VUELO_V1 = API_VERSION_1 + API_PRIVATE + API_RECURSO_RESERVAS_VUELO;

	private final RestClient restClientMuchoVuelo;

	public MuchoVueloClient( @Qualifier( "muchoVueloRestClient" ) RestClient restClientMuchoVuelo ) {
		this.restClientMuchoVuelo = restClientMuchoVuelo;
	}

	public ReservaVueloDTO creaReservaVuelo( ReservaVueloDTO reservaVuelo ) {
		String headerUsuario = getHeaderUsuario( reservaVuelo.getTipoDocumentoTitular(), reservaVuelo.getNumeroDocumentoTitular() );
		try {
			return restClientMuchoVuelo.post().uri( URI_RESERVAS_VUELO_V1 ).header( API_HEADER_USUARIO, headerUsuario ).body( reservaVuelo ).retrieve().body( ReservaVueloDTO.class );
		} catch ( org.springframework.web.client.RestClientResponseException ex ) {
			throw new MuchoVueloClientException( "MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(), ex );
		}
	}

	public void cancelarReservaVuelo( DocumentoIdentidad documentoIdentidadTitular, UUID idReserva ) {
		String headerUsuario = getHeaderUsuario( ApiRestV2Mapper.tipoDocumentoToDTO( documentoIdentidadTitular.tipo() ), documentoIdentidadTitular.identificador() );
		try {
			restClientMuchoVuelo.delete().uri( uriBuilder -> uriBuilder.path( URI_RESERVAS_VUELO_V1 + "/{idReserva}" ).build( idReserva ) ).header( API_HEADER_USUARIO, headerUsuario ).retrieve();
		} catch ( org.springframework.web.client.RestClientResponseException ex ) {
			throw new MuchoVueloClientException( "MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(), ex );
		}
	}

	private String getHeaderUsuario( TipoDocumentoDTO tipoDocumento, String identificador ) {
		return tipoDocumento.getCodigo() + ":" + identificador;
	}
}
