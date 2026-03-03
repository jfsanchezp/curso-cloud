package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DocumentoIdentidadDTO {

	@NotNull
	private TipoDocumento tipo;

	@NotBlank
	@Size( max = 15 )
	private String numero;

	public TipoDocumento getTipo() {
		return tipo;
	}

	public void setTipo( TipoDocumento tipo ) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero( String numero ) {
		this.numero = numero;
	}

}
