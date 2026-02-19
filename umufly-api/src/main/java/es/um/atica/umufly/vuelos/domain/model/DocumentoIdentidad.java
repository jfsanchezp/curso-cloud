package es.um.atica.umufly.vuelos.domain.model;

public record DocumentoIdentidad( TipoDocumento tipo, String identificador ) {

	private static final String PATRON_NIE = "[XYZ]\\d{7}[A-Z]";
	private static final String PATRON_NIF = "\\d{8}[A-Z]";
	private static final String LETRAS_CONTROL_NIE_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";

	public DocumentoIdentidad {
		if ( tipo == null ) {
			throw new IllegalArgumentException( "El tipo del documento de identidad no puede ser nulo" );
		}
		if ( identificador == null || identificador.isBlank() ) {
			throw new IllegalArgumentException( "El número del documento de identidad no puede ser nulo" );
		}

		String normalizado = identificador.trim().toUpperCase();

		if ( TipoDocumento.NIF.equals( tipo ) && !isValidNIF( normalizado ) ) {
			throw new IllegalArgumentException( "El número del documento no es un NIF válido" );
		}
		if ( TipoDocumento.NIE.equals( tipo ) && !isValidNIE( normalizado ) ) {
			throw new IllegalArgumentException( "El número del documento no es un NIE válido" );
		}

		identificador = normalizado;
	}

	private static boolean isValidNIF( String nif ) {
		if ( !nif.matches( PATRON_NIF ) ) {
			return false;
		}
		int numero = Integer.parseInt( nif.substring( 0, 8 ) );
		return isValidNumber( numero, nif.charAt( 8 ) );
	}

	private static boolean isValidNIE( String nie ) {
		if ( !nie.matches( PATRON_NIE ) ) {
			return false;
		}
		int prefijo = switch ( nie.charAt( 0 ) ) {
			case 'X' -> 0;
			case 'Y' -> 1;
			case 'Z' -> 2;
			default -> -1;
		};
		if ( prefijo < 0 ) {
			return false;
		}

		int numero = Integer.parseInt( prefijo + nie.substring( 1, 8 ) );
		return isValidNumber( numero, nie.charAt( 8 ) );
	}

	private static boolean isValidNumber( int numero, char letra ) {
		char letraCorrecta = LETRAS_CONTROL_NIE_NIF.charAt( numero % 23 );
		return letra == letraCorrecta;
	}
}
