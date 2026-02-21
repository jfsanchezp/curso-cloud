package es.um.atica.umufly.vuelos.adaptors.api.rest.v1.mapper;

import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.AvionDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.EstadoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.ItinerarioDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.TipoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.VueloDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;

public class ApiRestV1Mapper {

	private ApiRestV1Mapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static VueloDTO vueloToDTO( VueloAmpliadoDTO vuelo ) {
		return new VueloDTO( vuelo.getIdVuelo(), new ItinerarioDTO( vuelo.getFechaSalida(), vuelo.getFechaLlegada(), vuelo.getOrigen(), vuelo.getDestino() ), TipoVuelo.valueOf( vuelo.getTipoVuelo() ), EstadoVuelo.valueOf( vuelo.getEstadoVuelo() ),
				new AvionDTO( vuelo.getCapacidadAvion() ) );
	}

}
