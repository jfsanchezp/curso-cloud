package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table( name = "VW_RESERVA_VUELO", schema = "FORMACION_TICARUM" )
public class ReservaVueloViewEntity {

	@Id
	@NotNull
	@Column( name = "ID", nullable = false, length = 36 )
	private String id;

	@NotNull
	@Column( name = "TIPO_DOCUMENTO_TITULAR", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private TipoDocumentoEnum tipoDocumentoTitular;

	@NotNull
	@Column( name = "NUMERO_DOCUMENTO_TITULAR", length = 15, nullable = false )
	private String numeroDocumentoTitular;

	@NotNull
	@Column( name = "ID_VUELO", length = 36, nullable = false )
	private String idVuelo;

	@NotNull
	@Column( name = "CLASE_ASIENTO_RESERVA", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private ClaseAsientoReservaEnum claseAsientoReserva;

	@NotNull
	@Column( name = "FECHA_CREACION", nullable = false )
	private LocalDateTime fechaCreacion;


	@NotNull
	@Column( name = "FECHA_MODIFICACION", nullable = false )
	private LocalDateTime fechaModificacion;

	@NotNull
	@Column( name = "ESTADO_RESERVA", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private EstadoReservaVueloEnum estadoReserva;

	@Column( name = "FECHA_FORMALIZACION", nullable = true )
	private LocalDateTime fechaFormalizacion;

	@OneToMany( mappedBy = "reservaVuelo", cascade = CascadeType.ALL )
	private List<ReservaVueloPasajeroViewEntity> pasajeros;

	public void addPasajero( ReservaVueloPasajeroViewEntity pasajero ) {
		if ( pasajeros == null ) {
			pasajeros = new ArrayList<>();
		}
		pasajeros.add( pasajero );
		pasajero.setReservaVuelo( this );
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public TipoDocumentoEnum getTipoDocumentoTitular() {
		return tipoDocumentoTitular;
	}

	public void setTipoDocumentoTitular( TipoDocumentoEnum tipoDocumentoTitular ) {
		this.tipoDocumentoTitular = tipoDocumentoTitular;
	}

	public String getNumeroDocumentoTitular() {
		return numeroDocumentoTitular;
	}

	public void setNumeroDocumentoTitular( String numeroDocumentoTitular ) {
		this.numeroDocumentoTitular = numeroDocumentoTitular;
	}

	public String getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo( String idVuelo ) {
		this.idVuelo = idVuelo;
	}

	public ClaseAsientoReservaEnum getClaseAsientoReserva() {
		return claseAsientoReserva;
	}

	public void setClaseAsientoReserva( ClaseAsientoReservaEnum claseAsientoReserva ) {
		this.claseAsientoReserva = claseAsientoReserva;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion( LocalDateTime fechaCreacion ) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion( LocalDateTime fechaModificacion ) {
		this.fechaModificacion = fechaModificacion;
	}

	public EstadoReservaVueloEnum getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva( EstadoReservaVueloEnum estadoReserva ) {
		this.estadoReserva = estadoReserva;
	}

	public LocalDateTime getFechaFormalizacion() {
		return fechaFormalizacion;
	}

	public void setFechaFormalizacion( LocalDateTime fechaFormalizacion ) {
		this.fechaFormalizacion = fechaFormalizacion;
	}


	public List<ReservaVueloPasajeroViewEntity> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros( List<ReservaVueloPasajeroViewEntity> pasajeros ) {
		this.pasajeros = pasajeros;
	}


}
