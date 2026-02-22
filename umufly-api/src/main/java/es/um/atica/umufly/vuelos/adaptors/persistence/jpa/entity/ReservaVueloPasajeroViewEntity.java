package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table( name = "VW_RESERVA_VUELO_PASAJERO", schema = "FORMACION_TICARUM" )
public class ReservaVueloPasajeroViewEntity {

	@Id
	@NotNull
	@Column( name = "ID", nullable = false, length = 36 )
	private String id;

	@NotNull
	@ManyToOne
	@JoinColumn( name = "ID_RESERVA_VUELO", nullable = false )
	private ReservaVueloViewEntity reservaVuelo;

	@NotNull
	@Column( name = "TIPO_DOCUMENTO", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private TipoDocumentoEnum tipoDocumento;

	@NotNull
	@Column( name = "NUMERO_DOCUMENTO", length = 15, nullable = false )
	private String numeroDocumento;

	@NotNull
	@Column( name = "NOMBRE", length = 100, nullable = false )
	private String nombre;

	@NotNull
	@Column( name = "PRIMER_APELLIDO", length = 100, nullable = false )
	private String primerApellido;

	@Column( name = "SEGUNDO_APELLIDO", length = 100, nullable = true )
	private String segundoApellido;

	@NotNull
	@Column( name = "EMAIL", length = 300, nullable = false )
	private String email;

	@NotNull
	@Column( name = "NACIONALIDAD", length = 300, nullable = false )
	private String nacionalidad;


	public String getId() {
		return id;
	}


	public void setId( String id ) {
		this.id = id;
	}

	public ReservaVueloViewEntity getReservaVuelo() {
		return reservaVuelo;
	}

	public void setReservaVuelo( ReservaVueloViewEntity reservaVuelo ) {
		this.reservaVuelo = reservaVuelo;
	}


	public TipoDocumentoEnum getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento( TipoDocumentoEnum tipoDocumento ) {
		this.tipoDocumento = tipoDocumento;
	}


	public String getNumeroDocumento() {
		return numeroDocumento;
	}


	public void setNumeroDocumento( String numeroDocumento ) {
		this.numeroDocumento = numeroDocumento;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre( String nombre ) {
		this.nombre = nombre;
	}


	public String getPrimerApellido() {
		return primerApellido;
	}


	public void setPrimerApellido( String primerApellido ) {
		this.primerApellido = primerApellido;
	}


	public String getSegundoApellido() {
		return segundoApellido;
	}


	public void setSegundoApellido( String segundoApellido ) {
		this.segundoApellido = segundoApellido;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail( String email ) {
		this.email = email;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public void setNacionalidad( String nacionalidad ) {
		this.nacionalidad = nacionalidad;
	}

}
