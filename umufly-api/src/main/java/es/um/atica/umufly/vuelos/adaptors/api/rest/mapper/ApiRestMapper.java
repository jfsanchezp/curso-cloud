package es.um.atica.umufly.vuelos.adaptors.api.rest.mapper;

import es.um.atica.umufly.vuelos.adaptors.api.rest.dto.AvionDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.dto.EstadoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.dto.ItinerarioDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.dto.TipoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.dto.VueloDTO;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class ApiRestMapper {

	private ApiRestMapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static VueloDTO vueloToDTO( Vuelo vuelo ) {
		return new VueloDTO( vuelo.getId(), new ItinerarioDTO( vuelo.getItinerario().salida(), vuelo.getItinerario().llegada(), vuelo.getItinerario().origen(), vuelo.getItinerario().destino() ), TipoVuelo.valueOf( vuelo.getTipo().toString() ),
				EstadoVuelo.valueOf( vuelo.getEstado().toString() ), new AvionDTO( vuelo.getAvion().capacidad() ) );
	}

}
