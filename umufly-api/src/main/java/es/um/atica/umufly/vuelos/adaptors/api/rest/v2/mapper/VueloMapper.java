package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper;

import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.AvionDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.EstadoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ItinerarioDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.TipoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.VueloDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliado;

public class VueloMapper {

	private VueloMapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static VueloDTO vueloRestDTOFromApplicationDTO( VueloAmpliado vuelo ) {
		return new VueloDTO( vuelo.getIdVuelo(), new ItinerarioDTO( vuelo.getFechaSalida(), vuelo.getFechaLlegada(), vuelo.getOrigen(), vuelo.getDestino() ), TipoVuelo.valueOf( vuelo.getTipoVuelo() ), EstadoVuelo.valueOf( vuelo.getEstadoVuelo() ),
				new AvionDTO( vuelo.getCapacidadAvion() ) );
	}
}
