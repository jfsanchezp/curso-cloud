package es.um.atica.umufly.vuelos.application.mapper;

import java.util.Optional;
import java.util.UUID;

import es.um.atica.umufly.vuelos.application.dto.VueloAmpliado;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class VueloMapper {

	private VueloMapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static VueloAmpliado vueloModelToVueloAmpliado( Vuelo v, UUID idReserva ) {
		VueloAmpliado va = new VueloAmpliado();
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
