package es.um.atica.umufly.parking.adaptors.providers.muchovuelo.mapper;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoEnum;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.dto.ReservaParkingProviderDTO;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.dto.TipoDocumentoDTO;
import es.um.atica.umufly.parking.domain.model.ReservaParking;
import es.um.atica.umufly.parking.domain.model.TipoDocumento;
import es.um.atica.umufly.parking.domain.model.TipoEstacionamiento;

public final class MuchoVueloMapper {

	private MuchoVueloMapper() {
		throw new IllegalStateException( "Clase de conversión" );
	}

	public static ReservaParkingProviderDTO reservaToDTO( ReservaParking reserva ) {

		ReservaParkingProviderDTO dto = new ReservaParkingProviderDTO();

		dto.setEstadoReserva( reserva.getEstado() );
		dto.setFechaInicio( reserva.getPeriodoEstacionamiento().inicio() );
		dto.setFechaFin( reserva.getPeriodoEstacionamiento().fin() );
		dto.setNumeroDocumentoCliente( reserva.getIdentificadorCliente().identificador() );
		dto.setTipoDocumentoCliente( tipoDocumentoToDTO( reserva.getIdentificadorCliente().tipo() ) );
		dto.setImporte( reserva.getImporte().valor() );
		dto.setTipo( tipoEstacionamientoToEntity( reserva.getEstacionamiento().tipo() ) );

		return dto;
	}

	public static TipoDocumentoDTO tipoDocumentoToDTO( TipoDocumento tipo ) {
		return switch ( tipo ) {
			case NIF -> TipoDocumentoDTO.NIF;
			case NIE -> TipoDocumentoDTO.NIE;
			case PASAPORTE -> TipoDocumentoDTO.PASAPORTE;
		};
	}

	public static TipoEstacionamientoEnum tipoEstacionamientoToEntity( TipoEstacionamiento tipoEstacionamiento ) {
		return switch ( tipoEstacionamiento ) {
			case CORTA_DURACION -> TipoEstacionamientoEnum.C;
			case LARGA_DURACION -> TipoEstacionamientoEnum.L;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoEstacionamiento );
		};
	}
}
