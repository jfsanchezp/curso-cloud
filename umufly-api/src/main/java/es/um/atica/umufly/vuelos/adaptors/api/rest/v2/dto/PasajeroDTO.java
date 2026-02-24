package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonInclude( content = Include.NON_NULL )
public class PasajeroDTO {

	@Valid
	@NotNull
	private DocumentoIdentidadDTO documentoIdentidad;

	@NotBlank
	@Size( max = 100 )
	private String nombre;

	@NotBlank
	@Size( max = 100 )
	private String primerApellido;

	@Size( max = 100 )
	private String segundoApellido;

	@Email
	@NotNull
	private String correoElectronico;

	@NotBlank
	@Size( max = 300 )
	private String nacionalidad;

	public DocumentoIdentidadDTO getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad( DocumentoIdentidadDTO documentoIdentidad ) {
		this.documentoIdentidad = documentoIdentidad;
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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico( String correoElectronico ) {
		this.correoElectronico = correoElectronico;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad( String nacionalidad ) {
		this.nacionalidad = nacionalidad;
	}


}
