package es.um.atica.umufly.parking.adaptors.persistence.jpa.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.EstadoParkingEnum;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.ReservaParkingEntity;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.ReservaParkingViewEntity;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoDocumentoEnum;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoEnum;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoViewExtEntity;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.Estacionamiento;
import es.um.atica.umufly.parking.domain.model.EstadoParking;
import es.um.atica.umufly.parking.domain.model.Importe;
import es.um.atica.umufly.parking.domain.model.Periodo;
import es.um.atica.umufly.parking.domain.model.ReservaParking;
import es.um.atica.umufly.parking.domain.model.TipoDocumento;
import es.um.atica.umufly.parking.domain.model.TipoEstacionamiento;

public class JpaPersistenceMapper {

	private JpaPersistenceMapper() {
		throw new IllegalStateException( "Clase de utilidad" );
	}

	public static TipoDocumentoEnum tipoDocumentoToEntity( TipoDocumento tipoDocumento ) {
		return switch ( tipoDocumento ) {
			case NIF -> TipoDocumentoEnum.N;
			case NIE -> TipoDocumentoEnum.E;
			case PASAPORTE -> TipoDocumentoEnum.P;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoDocumento );
		};
	}

	public static TipoEstacionamientoEnum tipoEstacionamientoToEntity( TipoEstacionamiento tipoEstacionamiento ) {
		return switch ( tipoEstacionamiento ) {
			case CORTA_DURACION -> TipoEstacionamientoEnum.C;
			case LARGA_DURACION -> TipoEstacionamientoEnum.L;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoEstacionamiento );
		};
	}

	private static TipoEstacionamiento entityToTipoEstacionamiento( TipoEstacionamientoEnum tipoEstacionamiento ) {
		return switch ( tipoEstacionamiento ) {
			case C -> TipoEstacionamiento.CORTA_DURACION;
			case L -> TipoEstacionamiento.LARGA_DURACION;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoEstacionamiento );
		};
	}

	private static TipoDocumento tipoDocumentoEntityToModel( TipoDocumentoEnum tipoDocumento ) {
		return switch ( tipoDocumento ) {
			case N -> TipoDocumento.NIF;
			case E -> TipoDocumento.NIE;
			case P -> TipoDocumento.PASAPORTE;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoDocumento );
		};
	}

	private static EstadoParkingEnum estadoReservaToEntity( EstadoParking estadoReservaParking ) {
		return switch ( estadoReservaParking ) {
			case PENDIENTE -> EstadoParkingEnum.P;
			case ACTIVA -> EstadoParkingEnum.A;
			case CANCELADA -> EstadoParkingEnum.X;
			default -> throw new IllegalArgumentException( "Estado de la reserva no contemplado: " + estadoReservaParking );
		};
	}

	private static EstadoParking estadoReservaEntityToModel( EstadoParkingEnum estadoReservaParking ) {
		return switch ( estadoReservaParking ) {
			case P -> EstadoParking.PENDIENTE;
			case A -> EstadoParking.ACTIVA;
			case X -> EstadoParking.CANCELADA;
			default -> throw new IllegalArgumentException( "Estado de la reserva no contemplado: " + estadoReservaParking );
		};
	}

	public static ReservaParkingEntity reservaParkingToEntity( ReservaParking rp, LocalDateTime fechaReserva, LocalDateTime fechaModificacion ) {
		ReservaParkingEntity r = new ReservaParkingEntity();
		r.setId( rp.getId().toString() );
		r.setTipoDocumentoCliente( tipoDocumentoToEntity( rp.getIdentificadorCliente().tipo() ) );
		r.setNumeroDocumentoCliente( rp.getIdentificadorCliente().identificador() );
		r.setFechaReserva( fechaReserva );
		r.setFechaModificacion( fechaModificacion );
		r.setFechaInicio(rp.getPeriodoEstacionamiento().inicio());
		r.setFechaFin(rp.getPeriodoEstacionamiento().fin());
		r.setImporte(rp.getImporte().valor());
		r.setEstadoReserva( estadoReservaToEntity( rp.getEstado() ) );
		r.setTipo(tipoEstacionamientoToEntity(rp.getEstacionamiento().tipo()));
		return r;
	}

	public static ReservaParking reservaParkingToModel( ReservaParkingViewEntity r, TipoEstacionamientoViewExtEntity estacionamiento ) {
		return ReservaParking.of( UUID.fromString( r.getId() ), new DocumentoIdentidad( tipoDocumentoEntityToModel( r.getTipoDocumentoCliente() ), r.getNumeroDocumentoCliente() ),
				new Estacionamiento( entityToTipoEstacionamiento( estacionamiento.getTipo() ), estacionamiento.getPrecio() ),
				new Periodo( r.getFechaInicio(), r.getFechaFin() ),
				new Importe( r.getImporte() ), r.getFechaReserva(), estadoReservaEntityToModel( r.getEstadoReserva() ) );
	}
}
