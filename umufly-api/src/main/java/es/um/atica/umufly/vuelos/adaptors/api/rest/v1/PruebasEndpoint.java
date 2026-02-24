package es.um.atica.umufly.vuelos.adaptors.api.rest.v1;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;

@RestController
public class PruebasEndpoint {

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_1 + Constants.RESOURCE_PRUEBAS )
	public List<String> getPrivatePruebas() {
		return Arrays.asList( "priv-prueba-1", "priv-prueba-2" );
	}
}
