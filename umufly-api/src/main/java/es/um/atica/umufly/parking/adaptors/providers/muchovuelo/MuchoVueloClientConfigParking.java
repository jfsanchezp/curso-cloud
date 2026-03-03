package es.um.atica.umufly.parking.adaptors.providers.muchovuelo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.funtion.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class MuchoVueloClientConfigParking {

	@Value( "${umufly.vuelos.providers.muchovuelo.base-url}" )
	private String baseUrl;

	@Value( "${umufly.vuelos.providers.muchovuelo.base-path}" )
	private String basePath;

	@Bean
	public RestClient muchoVueloRestClientParking( RestClient.Builder builder ) {
		return builder.baseUrl( UriComponentsBuilder.fromUriString( baseUrl ).path( basePath ).build().toUri() ).build();
	}

}
