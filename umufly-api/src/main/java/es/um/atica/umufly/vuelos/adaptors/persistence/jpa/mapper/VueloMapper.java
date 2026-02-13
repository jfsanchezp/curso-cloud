package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper;

import java.util.UUID;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.VueloExtViewEntity;
import es.um.atica.umufly.vuelos.domain.model.Avion;
import es.um.atica.umufly.vuelos.domain.model.EstadoVuelo;
import es.um.atica.umufly.vuelos.domain.model.Itinerario;
import es.um.atica.umufly.vuelos.domain.model.TipoVuelo;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class VueloMapper {

	private VueloMapper() {
		throw new IllegalStateException( "Clase de utilidad" );
	}

	public static Vuelo vueloEntityToModel( VueloExtViewEntity v ) {
		return Vuelo.of( UUID.fromString( v.getId() ), new Itinerario( v.getFechaSalida(), v.getFechaLlegada(), v.getOrigen(), v.getDestino() ), tipoVueloEntityToModel( v.getTipoVuelo() ), estadoVueloEntityToModel( v.getEstadoVuelo() ),
				new Avion( v.getCapacidadAvion() ) );
	}

	private static TipoVuelo tipoVueloEntityToModel( TipoVueloEnum t ) {
		switch ( t ) {
			case I:
				return TipoVuelo.INTERNACIONAL;
			case N:
				return TipoVuelo.NACIONAL;
			default:
				throw new IllegalArgumentException( "Tipo de vuelo no soportado" );
		}
	}

	private static EstadoVuelo estadoVueloEntityToModel( EstadoVueloEnum e ) {
		switch ( e ) {
			case P:
				return EstadoVuelo.PENDIENTE;
			case R:
				return EstadoVuelo.RETRASADO;
			case C:
				return EstadoVuelo.COMPLETADO;
			case X:
				return EstadoVuelo.CANCELADO;
			default:
				throw new IllegalArgumentException( "Estado de vuelo no soportado" );
		}
	}
}
