package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table( name = "VW_RESERVA_VUELO_VIGENTE_PASAJERO", schema = "FORMACION_TICARUM" )
public class ReservaVueloVigentePasajeroViewEntity {

	@Id
	@NotNull
	@Column( name = "ID", nullable = false, length = 36 )
	private String id;

	@NotNull
	@Column( name = "ID_VUELO", nullable = false, length = 36 )
	private String idVuelo;

	@NotNull
	@Column( name = "TIPO_DOCUMENTO_PASAJERO", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private TipoDocumentoEnum tipoDocumento;

	@NotNull
	@Column( name = "NUMERO_DOCUMENTO_PASAJERO", length = 15, nullable = false )
	private String numeroDocumento;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo( String idVuelo ) {
		this.idVuelo = idVuelo;
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

}
