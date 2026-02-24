package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table( name = "VWEXT_VUELO", schema = "FORMACION_TICARUM" )
public class VueloExtViewEntity {

	@Id
	@NotNull
	@Column( name = "ID", nullable = false, length = 36 )
	private String id;

	@NotNull
	@Column( name = "FECHA_SALIDA", nullable = false )
	private LocalDateTime fechaSalida;

	@NotNull
	@Column( name = "FECHA_LLEGADA", nullable = false )
	private LocalDateTime fechaLlegada;

	@NotNull
	@Column( name = "TIPO_VUELO", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private TipoVueloEnum tipoVuelo;

	@NotNull
	@Column( name = "ORIGEN", length = 500, nullable = false )
	private String origen;

	@NotNull
	@Column( name = "DESTINO", length = 500, nullable = false )
	private String destino;

	@NotNull
	@Column( name = "ESTADO_VUELO", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private EstadoVueloEnum estadoVuelo;

	@NotNull
	@Column( name = "CAPACIDAD_AVION", nullable = false, scale = 0, precision = 5 )
	private int capacidadAvion;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida( LocalDateTime fechaSalida ) {
		this.fechaSalida = fechaSalida;
	}

	public LocalDateTime getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada( LocalDateTime fechaLlegada ) {
		this.fechaLlegada = fechaLlegada;
	}

	public TipoVueloEnum getTipoVuelo() {
		return tipoVuelo;
	}

	public void setTipoVuelo( TipoVueloEnum tipoVuelo ) {
		this.tipoVuelo = tipoVuelo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen( String origen ) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino( String destino ) {
		this.destino = destino;
	}

	public EstadoVueloEnum getEstadoVuelo() {
		return estadoVuelo;
	}

	public void setEstadoVuelo( EstadoVueloEnum estadoVuelo ) {
		this.estadoVuelo = estadoVuelo;
	}

	public int getCapacidadAvion() {
		return capacidadAvion;
	}

	public void setCapacidadAvion( int capacidadAvion ) {
		this.capacidadAvion = capacidadAvion;
	}

}
