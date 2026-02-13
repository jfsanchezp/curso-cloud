package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.mapper;

import java.util.List;

import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.ClaseAsiento;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.PasajeroDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.TipoDocumentoDTO;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;

public final class ReservaVueloMapper {

	private ReservaVueloMapper() {
		throw new IllegalStateException( "Clase de conversión" );
	}

	public static ReservaVueloDTO reservaVueloModelToDTO( ReservaVuelo reserva ) {

		ReservaVueloDTO dto = new ReservaVueloDTO();

		dto.setTipoDocumentoTitular( tipoDocumentoModelToDTO( reserva.getIdentificadorTitular().tipo() ) );
		dto.setNumeroDocumentoTitular( reserva.getIdentificadorTitular().identificador() );

		dto.setIdVuelo( reserva.getVuelo().getId() );
		dto.setClaseAsientoReserva( claseAsientoModelToDTO( reserva.getClase() ) );

		dto.setPasajeros( List.of( pasajeroModelToDTO( reserva.getPasajero() ) ) );

		return dto;
	}

	private static PasajeroDTO pasajeroModelToDTO( Pasajero pasajero ) {

		PasajeroDTO dto = new PasajeroDTO();

		dto.setTipoDocumento( tipoDocumentoModelToDTO( pasajero.getIdentificador().tipo() ) );
		dto.setNumeroDocumento( pasajero.getIdentificador().identificador() );

		dto.setNombre( pasajero.getNombre().nombre() );
		dto.setPrimerApellido( pasajero.getNombre().primerApellido() );
		dto.setSegundoApellido( pasajero.getNombre().segundoApellido() );

		dto.setEmail( pasajero.getCorreo().valor() );
		dto.setNacionalidad( pasajero.getNacionalidad().valor() );

		return dto;
	}

	private static TipoDocumentoDTO tipoDocumentoModelToDTO( TipoDocumento tipo ) {
		return switch ( tipo ) {
			case NIF -> TipoDocumentoDTO.NIF;
			case NIE -> TipoDocumentoDTO.NIE;
			case PASAPORTE -> TipoDocumentoDTO.PASAPORTE;
		};
	}

	private static ClaseAsiento claseAsientoModelToDTO( ClaseAsientoReserva clase ) {
		return switch ( clase ) {
			case ECONOMICA -> ClaseAsiento.ECONOMICA;
			case BUSINESS -> ClaseAsiento.BUSINESS;
			case PRIMERA -> ClaseAsiento.PRIMERA;
		};
	}
}
