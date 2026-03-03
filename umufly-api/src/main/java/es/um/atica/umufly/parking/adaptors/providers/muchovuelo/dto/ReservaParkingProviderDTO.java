package es.um.atica.umufly.parking.adaptors.providers.muchovuelo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoEnum;
import es.um.atica.umufly.parking.domain.model.EstadoParking;

public class ReservaParkingProviderDTO {

	private UUID id;

	@JsonProperty( "tipo_documento_cliente" )
	private TipoDocumentoDTO tipoDocumentoCliente;

	@JsonProperty( "numero_documento_cliente" )
	private String numeroDocumentoCliente;

	@JsonProperty( "fecha_inicio" )
	private LocalDateTime fechaInicio;

	@JsonProperty( "fecha_fin" )
	private LocalDateTime fechaFin;

	@JsonProperty( "estado_reserva" )
	private EstadoParking estadoReserva;

	@JsonProperty( "tipo_estacionamiento" )
	private TipoEstacionamientoEnum tipo;

	@JsonProperty( "importe" )
	private double importe;

	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public TipoDocumentoDTO getTipoDocumentoCliente() {
		return tipoDocumentoCliente;
	}

	public void setTipoDocumentoCliente( TipoDocumentoDTO tipoDocumentoCliente ) {
		this.tipoDocumentoCliente = tipoDocumentoCliente;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente( String numeroDocumentoCliente ) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin( LocalDateTime fechaFin ) {
		this.fechaFin = fechaFin;
	}

	public TipoEstacionamientoEnum getTipo() {
		return tipo;
	}

	public void setTipo( TipoEstacionamientoEnum tipo ) {
		this.tipo = tipo;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte( double importe ) {
		this.importe = importe;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio( LocalDateTime fechaInicio ) {
		this.fechaInicio = fechaInicio;
	}

	public EstadoParking getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva( EstadoParking estadoReserva ) {
		this.estadoReserva = estadoReserva;
	}
}
