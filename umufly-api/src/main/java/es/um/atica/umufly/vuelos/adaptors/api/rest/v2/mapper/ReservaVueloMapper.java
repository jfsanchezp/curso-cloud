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
import es.um.atica.umufly.vuelos.domain.model.CorreoElectronico;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Nacionalidad;
import es.um.atica.umufly.vuelos.domain.model.NombreCompleto;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class ReservaVueloMapper {

	private ReservaVueloMapper() {
		throw new IllegalStateException( "Clase de conversión" );
	}

	public static Pasajero pasajeroDTOToModel( PasajeroDTO pasajero ) {
		return Pasajero.of( new DocumentoIdentidad( TipoDocumento.valueOf( pasajero.getDocumentoIdentidad().getTipo().toString() ), pasajero.getDocumentoIdentidad().getNumero() ),
				new NombreCompleto( pasajero.getNombre(), pasajero.getPrimerApellido(), pasajero.getSegundoApellido() ), new CorreoElectronico( pasajero.getCorreoElectronico() ), new Nacionalidad( pasajero.getNacionalidad() ) );
	}

	public static ReservaVueloDTO reservaVueloRestDTOFromModel( ReservaVuelo reservaVuelo ) {
		ReservaVueloDTO reservaVueloDTO = new ReservaVueloDTO();
		reservaVueloDTO.setId( reservaVuelo.getId() );
		reservaVueloDTO.setVuelo( vueloDTOFromModel( reservaVuelo.getVuelo() ) );
		reservaVueloDTO.setPasajero( pasajeroDTOFromModel( reservaVuelo.getPasajero() ) );
		reservaVueloDTO.setDocumentoIdentidadTitular( documentoIdentidadDTOFromModel( reservaVuelo.getIdentificadorTitular() ) );
		reservaVueloDTO.setClaseAsiento( ClaseAsientoReserva.valueOf( reservaVuelo.getClase().toString() ) );
		reservaVueloDTO.setEstado( EstadoReserva.valueOf( reservaVuelo.getEstado().toString() ) );
		reservaVueloDTO.setFechaReserva( reservaVuelo.getFechaReserva() );
		return reservaVueloDTO;
	}

	private static PasajeroDTO pasajeroDTOFromModel( Pasajero pasajero ) {
		PasajeroDTO pasajeroDTO = new PasajeroDTO();
		pasajeroDTO.setDocumentoIdentidad( documentoIdentidadDTOFromModel( pasajero.getIdentificador() ) );
		pasajeroDTO.setNombre( pasajero.getNombre().nombre() );
		pasajeroDTO.setPrimerApellido( pasajero.getNombre().primerApellido() );
		pasajeroDTO.setSegundoApellido( pasajero.getNombre().segundoApellido() );
		pasajeroDTO.setCorreoElectronico( pasajero.getCorreo().valor() );
		pasajeroDTO.setNacionalidad( pasajero.getNacionalidad().valor() );
		return pasajeroDTO;
	}

	private static DocumentoIdentidadDTO documentoIdentidadDTOFromModel( DocumentoIdentidad documentoIdentidad ) {
		DocumentoIdentidadDTO documentoIdentidadDTO = new DocumentoIdentidadDTO();
		documentoIdentidadDTO.setTipo( es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.TipoDocumento.valueOf( documentoIdentidad.tipo().toString() ) );
		documentoIdentidadDTO.setNumero( documentoIdentidad.identificador() );
		return documentoIdentidadDTO;
	}

	private static VueloDTO vueloDTOFromModel( Vuelo vv ) {
		return new VueloDTO( vv.getId(), new ItinerarioDTO( vv.getItinerario().salida(), vv.getItinerario().llegada(), vv.getItinerario().origen(), vv.getItinerario().destino() ), TipoVuelo.valueOf( vv.getTipo().toString() ),
				EstadoVuelo.valueOf( vv.getEstado().toString() ), new AvionDTO( vv.getAvion().capacidad() ) );
	}

}
