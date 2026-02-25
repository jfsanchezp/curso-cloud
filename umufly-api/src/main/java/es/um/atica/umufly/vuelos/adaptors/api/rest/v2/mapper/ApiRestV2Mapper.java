package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.mapper;

import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.AvionDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.DocumentoIdentidadDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.EstadoReserva;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.EstadoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ItinerarioDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.PasajeroDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.TipoVuelo;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.VueloDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.TipoDocumentoDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.domain.model.CorreoElectronico;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Nacionalidad;
import es.um.atica.umufly.vuelos.domain.model.NombreCompleto;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class ApiRestV2Mapper {

	private ApiRestV2Mapper() {
		throw new IllegalArgumentException( "Clase de conversión" );
	}

	public static VueloDTO vueloToDTO( VueloAmpliadoDTO vuelo ) {
		return new VueloDTO( vuelo.getIdVuelo(), new ItinerarioDTO( vuelo.getFechaSalida(), vuelo.getFechaLlegada(), vuelo.getOrigen(), vuelo.getDestino() ), TipoVuelo.valueOf( vuelo.getTipoVuelo() ), EstadoVuelo.valueOf( vuelo.getEstadoVuelo() ),
				new AvionDTO( vuelo.getCapacidadAvion() ) );
	}

	public static Pasajero pasajeroToModel( PasajeroDTO pasajero ) {
		return Pasajero.of( new DocumentoIdentidad( TipoDocumento.valueOf( pasajero.getDocumentoIdentidad().getTipo().toString() ), pasajero.getDocumentoIdentidad().getNumero() ),
				new NombreCompleto( pasajero.getNombre(), pasajero.getPrimerApellido(), pasajero.getSegundoApellido() ), new CorreoElectronico( pasajero.getCorreoElectronico() ), new Nacionalidad( pasajero.getNacionalidad() ) );
	}

	public static ReservaVueloDTO reservaVueloToDTO( ReservaVuelo reservaVuelo ) {
		ReservaVueloDTO reservaVueloDTO = new ReservaVueloDTO();
		reservaVueloDTO.setId( reservaVuelo.getId() );
		reservaVueloDTO.setVuelo( vueloToDTO( reservaVuelo.getVuelo() ) );
		reservaVueloDTO.setPasajero( pasajeroToDTO( reservaVuelo.getPasajero() ) );
		reservaVueloDTO.setDocumentoIdentidadTitular( documentoIdentidadToDTO( reservaVuelo.getIdentificadorTitular() ) );
		reservaVueloDTO.setClaseAsiento( ClaseAsientoReserva.valueOf( reservaVuelo.getClase().toString() ) );
		reservaVueloDTO.setEstado( EstadoReserva.valueOf( reservaVuelo.getEstado().toString() ) );
		reservaVueloDTO.setFechaReserva( reservaVuelo.getFechaReserva() );
		return reservaVueloDTO;
	}

	private static PasajeroDTO pasajeroToDTO( Pasajero pasajero ) {
		PasajeroDTO pasajeroDTO = new PasajeroDTO();
		pasajeroDTO.setDocumentoIdentidad( documentoIdentidadToDTO( pasajero.getIdentificador() ) );
		pasajeroDTO.setNombre( pasajero.getNombre().nombre() );
		pasajeroDTO.setPrimerApellido( pasajero.getNombre().primerApellido() );
		pasajeroDTO.setSegundoApellido( pasajero.getNombre().segundoApellido() );
		pasajeroDTO.setCorreoElectronico( pasajero.getCorreo().valor() );
		pasajeroDTO.setNacionalidad( pasajero.getNacionalidad().valor() );
		return pasajeroDTO;
	}

	private static DocumentoIdentidadDTO documentoIdentidadToDTO( DocumentoIdentidad documentoIdentidad ) {
		DocumentoIdentidadDTO documentoIdentidadDTO = new DocumentoIdentidadDTO();
		documentoIdentidadDTO.setTipo( es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.TipoDocumento.valueOf( documentoIdentidad.tipo().toString() ) );
		documentoIdentidadDTO.setNumero( documentoIdentidad.identificador() );
		return documentoIdentidadDTO;
	}

	private static VueloDTO vueloToDTO( Vuelo vv ) {
		return new VueloDTO( vv.getId(), new ItinerarioDTO( vv.getItinerario().salida(), vv.getItinerario().llegada(), vv.getItinerario().origen(), vv.getItinerario().destino() ), TipoVuelo.valueOf( vv.getTipo().toString() ),
				EstadoVuelo.valueOf( vv.getEstado().toString() ), new AvionDTO( vv.getAvion().capacidad() ) );
	}

	public static TipoDocumentoDTO tipoDocumentoToDTO( TipoDocumento tipoDocumento ) {
		return switch ( tipoDocumento ) {
			case NIF -> TipoDocumentoDTO.NIF;
			case NIE -> TipoDocumentoDTO.NIE;
			case PASAPORTE -> TipoDocumentoDTO.PASAPORTE;
			default -> throw new IllegalArgumentException( "Unexpected value: " + tipoDocumento );
		};
	}
}
