package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasajeroDTO {

	private UUID id;

	@JsonProperty( "tipo_documento" )
	private TipoDocumentoDTO tipoDocumento;

	@JsonProperty( "numero_documento" )
	private String numeroDocumento;

	private String nombre;

	@JsonProperty( "primer_apellido" )
	private String primerApellido;

	@JsonProperty( "segundo_apellido" )
	private String segundoApellido;

	private String email;

	private String nacionalidad;

	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento( TipoDocumentoDTO tipoDocumento ) {
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
