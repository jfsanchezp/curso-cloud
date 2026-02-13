package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ClaseAsientoReservaEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloPasajeroEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoDocumentoEnum;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.EstadoReserva;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;

public class ReservaVueloMapper {

	private ReservaVueloMapper() {
		throw new IllegalStateException( "Clase de utilidad" );
	}

	public static TipoDocumentoEnum tipoDocumentoEntityFromModel(TipoDocumento tipoDocumento) {
		return switch (tipoDocumento) {
			case NIF -> TipoDocumentoEnum.N;
			case NIE -> TipoDocumentoEnum.E;
			case PASAPORTE -> TipoDocumentoEnum.P;
			default ->
			throw new IllegalArgumentException( "Tipo de documento no contemplado: " + tipoDocumento );
		};
	}

	public static ClaseAsientoReservaEnum claseAsientoReservaEntityFromModel( ClaseAsientoReserva claseAsiento ) {
		return switch ( claseAsiento ) {
			case ECONOMICA -> ClaseAsientoReservaEnum.E;
			case BUSINESS -> ClaseAsientoReservaEnum.B;
			case PRIMERA -> ClaseAsientoReservaEnum.P;
			default -> throw new IllegalArgumentException( "Clase de asiento no contemplada: " + claseAsiento );
		};
	}

	public static EstadoReservaVueloEnum estadoReservaEntityFromModel( EstadoReserva estadoReserva ) {
		return switch ( estadoReserva ) {
			case PENDIENTE -> EstadoReservaVueloEnum.P;
			case ACTIVA -> EstadoReservaVueloEnum.A;
			case CANCELADA -> EstadoReservaVueloEnum.X;
			default -> throw new IllegalArgumentException( "Estado de la reserva no contemplado: " + estadoReserva );
		};
	}

	public static ReservaVueloEntity reservaVueloModelToEntity( ReservaVuelo rr, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion ) {
		ReservaVueloEntity r = new ReservaVueloEntity();
		r.setId( rr.getId().toString() );
		r.setTipoDocumentoTitular( tipoDocumentoEntityFromModel( rr.getIdentificadorTitular().tipo() ) );
		r.setNumeroDocumentoTitular( rr.getIdentificadorTitular().identificador() );
		r.setIdVuelo( rr.getVuelo().getId().toString() );
		r.setClaseAsientoReserva( claseAsientoReservaEntityFromModel( rr.getClase() ) );
		r.setFechaCreacion( fechaCreacion );
		r.setFechaModificacion( fechaModificacion );
		r.setEstadoReserva( estadoReservaEntityFromModel( rr.getEstado() ) );
		r.addPasajero( pasajeroModelToEntity( rr.getPasajero() ) );
		return r;
	}

	private static ReservaVueloPasajeroEntity pasajeroModelToEntity( Pasajero pp ) {
		ReservaVueloPasajeroEntity p = new ReservaVueloPasajeroEntity();
		p.setId( UUID.randomUUID().toString() );
		p.setTipoDocumento( tipoDocumentoEntityFromModel( pp.getIdentificador().tipo() ) );
		p.setNumeroDocumento( pp.getIdentificador().identificador() );
		p.setNombre( pp.getNombre().nombre() );
		p.setPrimerApellido( pp.getNombre().primerApellido() );
		p.setSegundoApellido( pp.getNombre().segundoApellido() );
		p.setEmail( pp.getCorreo().valor() );
		p.setNacionalidad( pp.getNacionalidad().valor() );
		return p;
	}
}
