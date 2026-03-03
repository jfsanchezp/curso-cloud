package es.um.atica.umufly.parking.adaptors.api.rest.mapper;

import es.um.atica.umufly.parking.adaptors.api.rest.dto.DocumentoIdentidadDTO;
import es.um.atica.umufly.parking.adaptors.api.rest.dto.EstadoParking;
import es.um.atica.umufly.parking.adaptors.api.rest.dto.ReservaParkingDTO;
import es.um.atica.umufly.parking.adaptors.api.rest.dto.TipoDocumento;
import es.um.atica.umufly.parking.adaptors.api.rest.dto.TipoEstacionamiento;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

public class ApiRestMapper {

	private ApiRestMapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static ReservaParkingDTO reservaParkingToDTO( ReservaParking reservaParking ) {
		ReservaParkingDTO reservaParkingDTO = new ReservaParkingDTO();
		reservaParkingDTO.setId( reservaParking.getId() );
		reservaParkingDTO.setDocumentoIdentidadTitular( documentoIdentidadToDTO( reservaParking.getIdentificadorCliente() ) );
		reservaParkingDTO.setFechaReserva( reservaParking.getFechaReserva() );
		reservaParkingDTO.setFechaInicio( reservaParking.getPeriodoEstacionamiento().inicio() );
		reservaParkingDTO.setFechaFin( reservaParking.getPeriodoEstacionamiento().fin() );
		reservaParkingDTO.setEstado( EstadoParking.valueOf( reservaParking.getEstado().toString() ) );
		reservaParkingDTO.setTipo( TipoEstacionamiento.valueOf( reservaParking.getEstacionamiento().tipo().toString() ) );
		reservaParkingDTO.setImporte( reservaParking.getImporte().valor() );
		return reservaParkingDTO;
	}

	private static DocumentoIdentidadDTO documentoIdentidadToDTO( DocumentoIdentidad documentoIdentidad ) {
		DocumentoIdentidadDTO documentoIdentidadDTO = new DocumentoIdentidadDTO();
		documentoIdentidadDTO.setTipo( TipoDocumento.valueOf( documentoIdentidad.tipo().toString() ) );
		documentoIdentidadDTO.setNumero( documentoIdentidad.identificador() );
		return documentoIdentidadDTO;
	}

	// public static TipoDocumentoDTO tipoDocumentoToDTO( TipoDocumento tipoDocumento ) {
	// return switch ( tipoDocumento ) {
	// case NIF -> TipoDocumentoDTO.NIE;
	// case NIE -> TipoDocumentoDTO.NIE;
	// case PASAPORTE -> TipoDocumentoDTO.PASAPORTE;
	// default -> throw new IllegalArgumentException( "Unexpected value: " + tipoDocumento );
	// };
	// }
}
