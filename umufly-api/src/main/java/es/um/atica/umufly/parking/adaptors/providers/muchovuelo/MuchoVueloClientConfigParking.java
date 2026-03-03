package es.um.atica.umufly.parking.adaptors.providers.muchovuelo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class MuchoVueloClientConfigParking {

	@Value( "${umufly.vuelos.providers.muchovuelo.base-url}" )
	private String baseUrl;

	@Value( "${umufly.vuelos.providers.muchovuelo.base-path}" )
	private String basePath;

	@Bean
	public RestTemplate muchoVueloRestTemplateParking( RestTemplateBuilder builder ) {
		// Construimos la URL completa de forma segura
		String rootUri = UriComponentsBuilder.fromUriString( baseUrl ).path( basePath ).build().toUriString();

		// RestTemplateBuilder en 2.7 permite fijar la rootUri para que
		// los clientes usen paths relativos
		return builder.rootUri( rootUri ).build();
	}
}
