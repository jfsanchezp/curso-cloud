package es.um.atica.umufly.parking.adaptors.api.rest;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.TipoDocumento;

@Component
public class AuthServiceParking {

	public DocumentoIdentidad parseUserHeader( String userHeader ) {
		if ( userHeader == null ) {
			throw new IllegalArgumentException( "La cabecera no tiene un formato correcto" );
		}

		String[] documentoIdentidad = userHeader.split( ":" );
		if ( documentoIdentidad.length != 2 ) {
			throw new IllegalArgumentException( "La cabecera no tiene un formato correcto" );
		}

		return new DocumentoIdentidad( TipoDocumento.valueOf( documentoIdentidad[0] ), documentoIdentidad[1] );
	}

}
