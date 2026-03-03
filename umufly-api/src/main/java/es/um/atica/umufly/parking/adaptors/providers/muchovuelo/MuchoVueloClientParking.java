package es.um.atica.umufly.parking.adaptors.providers.muchovuelo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.dto.ReservaParkingProviderDTO;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.dto.TipoDocumentoDTO;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.exception.MuchoVueloClientException;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.mapper.MuchoVueloMapper;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;

@Component
public class MuchoVueloClientParking {

	private static final String API_VERSION_1 = "/v1.0";
	private static final String API_PRIVATE = "/private";
	private static final String API_RECURSO_RESERVAS_PARKING = "/reservas-parking";
	private static final String API_HEADER_USUARIO = "X-Usuario";

	private static final String URI_RESERVAS_VUELO_V1 = API_VERSION_1 + API_PRIVATE + API_RECURSO_RESERVAS_PARKING;

	private final RestClient restClientMuchoVuelo;

	public MuchoVueloClientParking( @Qualifier( "muchoVueloRestClient" ) RestClient restClientMuchoVuelo ) {
		this.restClientMuchoVuelo = restClientMuchoVuelo;
	}

	public ReservaParkingProviderDTO crearParking( ReservaParkingProviderDTO reserva ) {
		String headerUsuario = getHeaderUsuario( reserva.getTipoDocumentoCliente(), reserva.getNumeroDocumentoCliente() );
		try {
			return restClientMuchoVuelo.post().uri( URI_RESERVAS_VUELO_V1 ).header( API_HEADER_USUARIO, headerUsuario ).body( reserva ).retrieve().body( ReservaParkingProviderDTO.class );
		} catch ( org.springframework.web.client.RestClientResponseException ex ) {
			throw new MuchoVueloClientException( "MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(), ex );
		}
	}

	public void cancelarParking( DocumentoIdentidad documentoIdentidadTitular, UUID idParking ) {
		String headerUsuario = getHeaderUsuario( MuchoVueloMapper.tipoDocumentoToDTO( documentoIdentidadTitular.tipo() ), documentoIdentidadTitular.identificador() );
		try {
			restClientMuchoVuelo.delete().uri( uriBuilder -> uriBuilder.path( URI_RESERVAS_VUELO_V1 + "/{idParking}" ).build( idParking ) ).header( API_HEADER_USUARIO, headerUsuario ).retrieve();
		} catch ( org.springframework.web.client.RestClientResponseException ex ) {
			throw new MuchoVueloClientException( "MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(), ex );
		}
	}

	private String getHeaderUsuario( TipoDocumentoDTO tipoDocumento, String identificador ) {
		return tipoDocumento.getCodigo() + ":" + identificador;
	}
}
