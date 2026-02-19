package es.um.atica.umufly.vuelos.domain.model;

public class Pasajero {

	private DocumentoIdentidad identificador;
	private NombreCompleto nombre;
	private CorreoElectronico correo;
	private Nacionalidad nacionalidad;

	private Pasajero( DocumentoIdentidad identificador, NombreCompleto nombre, CorreoElectronico correo, Nacionalidad nacionalidad ) {
		this.identificador = identificador;
		this.nombre = nombre;
		this.correo = correo;
		this.nacionalidad = nacionalidad;
	}

	public static Pasajero of( DocumentoIdentidad identificador, NombreCompleto nombre, CorreoElectronico correo, Nacionalidad nacionalidad ) {
		if ( identificador == null ) {
			throw new IllegalArgumentException( "El identificador del pasajero no puede ser nulo" );
		}
		if ( nombre == null ) {
			throw new IllegalArgumentException( "El nombre del pasajero no puede ser nulo" );
		}
		if ( correo == null ) {
			throw new IllegalArgumentException( "El correo del pasajero no puede ser nulo" );
		}
		if ( nacionalidad == null ) {
			throw new IllegalArgumentException( "La nacionalidad del pasajero no puede ser nula" );
		}
		return new Pasajero( identificador, nombre, correo, nacionalidad );
	}

	public DocumentoIdentidad getIdentificador() {
		return identificador;
	}

	public NombreCompleto getNombre() {
		return nombre;
	}

	public CorreoElectronico getCorreo() {
		return correo;
	}

	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}

}
