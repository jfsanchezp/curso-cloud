package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ClaseAsientoReservaEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloPasajeroEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoDocumentoEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.VueloExtViewEntity;
import es.um.atica.umufly.vuelos.domain.model.Avion;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.CorreoElectronico;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.EstadoReserva;
import es.um.atica.umufly.vuelos.domain.model.EstadoVuelo;
import es.um.atica.umufly.vuelos.domain.model.Itinerario;
import es.um.atica.umufly.vuelos.domain.model.Nacionalidad;
import es.um.atica.umufly.vuelos.domain.model.NombreCompleto;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;
import es.um.atica.umufly.vuelos.domain.model.TipoVuelo;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class JpaPersistenceMapper {

	private JpaPersistenceMapper() {
		throw new IllegalStateException( "Clase de utilidad" );
	}

	public static Vuelo vueloToModel( VueloExtViewEntity v ) {
		return Vuelo.of( UUID.fromString( v.getId() ), new Itinerario( v.getFechaSalida(), v.getFechaLlegada(), v.getOrigen(), v.getDestino() ), tipoVueloToModel( v.getTipoVuelo() ), estadoVueloToModel( v.getEstadoVuelo() ),
				new Avion( v.getCapacidadAvion() ) );
	}

	public static TipoDocumentoEnum tipoDocumentoToEntity( TipoDocumento tipoDocumento ) {
		return switch ( tipoDocumento ) {
			case NIF -> TipoDocumentoEnum.N;
			case NIE -> TipoDocumentoEnum.E;
			case PASAPORTE -> TipoDocumentoEnum.P;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoDocumento );
		};
	}

	public static TipoDocumento tipoDocumentoEntityToModel( TipoDocumentoEnum tipoDocumento ) {
		return switch ( tipoDocumento ) {
			case N -> TipoDocumento.NIF;
			case E -> TipoDocumento.NIE;
			case P -> TipoDocumento.PASAPORTE;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoDocumento );
		};
	}

	private static TipoVuelo tipoVueloToModel( TipoVueloEnum t ) {
		switch ( t ) {
			case I:
				return TipoVuelo.INTERNACIONAL;
			case N:
				return TipoVuelo.NACIONAL;
			default:
				throw new IllegalArgumentException( "Tipo de vuelo no soportado" );
		}
	}

	private static EstadoVuelo estadoVueloToModel( EstadoVueloEnum e ) {
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

	public static ClaseAsientoReservaEnum claseAsientoReservaToEntity( ClaseAsientoReserva claseAsiento ) {
		return switch ( claseAsiento ) {
			case ECONOMICA -> ClaseAsientoReservaEnum.E;
			case BUSINESS -> ClaseAsientoReservaEnum.B;
			case PRIMERA -> ClaseAsientoReservaEnum.P;
			default -> throw new IllegalArgumentException( "Clase de asiento no contemplada: " + claseAsiento );
		};
	}

	public static ClaseAsientoReserva claseAsientoReservaEntityToModel( ClaseAsientoReservaEnum claseAsiento ) {
		return switch ( claseAsiento ) {
			case E -> ClaseAsientoReserva.ECONOMICA;
			case B -> ClaseAsientoReserva.BUSINESS;
			case P -> ClaseAsientoReserva.PRIMERA;
			default -> throw new IllegalArgumentException( "Clase de asiento no contemplada: " + claseAsiento );
		};
	}

	public static EstadoReservaVueloEnum estadoReservaToEntity( EstadoReserva estadoReserva ) {
		return switch ( estadoReserva ) {
			case PENDIENTE -> EstadoReservaVueloEnum.P;
			case ACTIVA -> EstadoReservaVueloEnum.A;
			case CANCELADA -> EstadoReservaVueloEnum.X;
			default -> throw new IllegalArgumentException( "Estado de la reserva no contemplado: " + estadoReserva );
		};
	}

	public static EstadoReserva estadoReservaEntityToModel( EstadoReservaVueloEnum estadoReserva ) {
		return switch ( estadoReserva ) {
			case P -> EstadoReserva.PENDIENTE;
			case A -> EstadoReserva.ACTIVA;
			case X -> EstadoReserva.CANCELADA;
			default -> throw new IllegalArgumentException( "Estado de la reserva no contemplado: " + estadoReserva );
		};
	}

	public static ReservaVueloEntity reservaVueloToEntity( ReservaVuelo rr, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion ) {
		ReservaVueloEntity r = new ReservaVueloEntity();
		r.setId( rr.getId().toString() );
		r.setTipoDocumentoTitular( tipoDocumentoToEntity( rr.getIdentificadorTitular().tipo() ) );
		r.setNumeroDocumentoTitular( rr.getIdentificadorTitular().identificador() );
		r.setIdVuelo( rr.getVuelo().getId().toString() );
		r.setClaseAsientoReserva( claseAsientoReservaToEntity( rr.getClase() ) );
		r.setFechaCreacion( fechaCreacion );
		r.setFechaModificacion( fechaModificacion );
		r.setEstadoReserva( estadoReservaToEntity( rr.getEstado() ) );
		r.addPasajero( pasajeroToEntity( rr.getPasajero() ) );
		return r;
	}

	public static ReservaVuelo reservaVueloToModel( ReservaVueloEntity r, VueloExtViewEntity v ) {
		return ReservaVuelo.of( UUID.fromString( r.getId() ), new DocumentoIdentidad( tipoDocumentoEntityToModel( r.getTipoDocumentoTitular() ), r.getNumeroDocumentoTitular() ),
				r.getPasajeros() == null || r.getPasajeros().isEmpty() ? null : pasajeroToModel( r.getPasajeros().get( 0 ) ), v != null ? vueloToModel( v ) : null, claseAsientoReservaEntityToModel( r.getClaseAsientoReserva() ), r.getFechaCreacion(),
				estadoReservaEntityToModel( r.getEstadoReserva() ) );
	}

	private static ReservaVueloPasajeroEntity pasajeroToEntity( Pasajero pp ) {
		ReservaVueloPasajeroEntity p = new ReservaVueloPasajeroEntity();
		p.setId( UUID.randomUUID().toString() );
		p.setTipoDocumento( tipoDocumentoToEntity( pp.getIdentificador().tipo() ) );
		p.setNumeroDocumento( pp.getIdentificador().identificador() );
		p.setNombre( pp.getNombre().nombre() );
		p.setPrimerApellido( pp.getNombre().primerApellido() );
		p.setSegundoApellido( pp.getNombre().segundoApellido() );
		p.setEmail( pp.getCorreo().valor() );
		p.setNacionalidad( pp.getNacionalidad().valor() );
		return p;
	}

	public static Pasajero pasajeroToModel( ReservaVueloPasajeroEntity p ) {
		return Pasajero.of( new DocumentoIdentidad( tipoDocumentoEntityToModel( p.getTipoDocumento() ), p.getNumeroDocumento() ), new NombreCompleto( p.getNombre(), p.getPrimerApellido(), p.getSegundoApellido() ), new CorreoElectronico( p.getEmail() ),
				new Nacionalidad( p.getNacionalidad() ) );
	}
}
