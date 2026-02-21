package es.um.atica.umufly.vuelos.application.mapper;

import java.util.Optional;
import java.util.UUID;

import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class ApplicationMapper {

	private ApplicationMapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static VueloAmpliadoDTO vueloToDTO( Vuelo v, UUID idReserva ) {
		VueloAmpliadoDTO va = new VueloAmpliadoDTO();
		va.setIdVuelo( v.getId() );
		va.setFechaSalida( v.getItinerario().salida() );
		va.setFechaLlegada( v.getItinerario().llegada() );
		va.setOrigen( v.getItinerario().origen() );
		va.setDestino( v.getItinerario().destino() );
		va.setTipoVuelo( v.getTipo().toString() );
		va.setEstadoVuelo( v.getEstado().toString() );
		va.setCapacidadAvion( v.getAvion().capacidad() );
		va.setIdReserva( Optional.ofNullable( idReserva ) );
		return va;
	}

}
