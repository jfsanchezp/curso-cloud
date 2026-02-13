package es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaVueloDTO {

	private UUID id;

	@JsonProperty( "tipo_documento_titular" )
	private TipoDocumentoDTO tipoDocumentoTitular;

	@JsonProperty( "numero_documento_titular" )
	private String numeroDocumentoTitular;

	@JsonProperty( "id_vuelo" )
	private UUID idVuelo;

	@JsonProperty( "clase_asiento_reserva" )
	private ClaseAsiento claseAsientoReserva;

	@JsonProperty( "fecha_reserva" )
	private LocalDateTime fechaReserva;

	@JsonProperty( "estado_reserva" )
	private EstadoReserva estadoReserva;

	private List<PasajeroDTO> pasajeros;

	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public TipoDocumentoDTO getTipoDocumentoTitular() {
		return tipoDocumentoTitular;
	}

	public void setTipoDocumentoTitular( TipoDocumentoDTO tipoDocumentoTitular ) {
		this.tipoDocumentoTitular = tipoDocumentoTitular;
	}

	public String getNumeroDocumentoTitular() {
		return numeroDocumentoTitular;
	}

	public void setNumeroDocumentoTitular( String numeroDocumentoTitular ) {
		this.numeroDocumentoTitular = numeroDocumentoTitular;
	}

	public UUID getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo( UUID idVuelo ) {
		this.idVuelo = idVuelo;
	}

	public ClaseAsiento getClaseAsientoReserva() {
		return claseAsientoReserva;
	}

	public void setClaseAsientoReserva( ClaseAsiento claseAsientoReserva ) {
		this.claseAsientoReserva = claseAsientoReserva;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva( LocalDateTime fechaReserva ) {
		this.fechaReserva = fechaReserva;
	}

	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva( EstadoReserva estadoReserva ) {
		this.estadoReserva = estadoReserva;
	}

	public List<PasajeroDTO> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros( List<PasajeroDTO> pasajeros ) {
		this.pasajeros = pasajeros;
	}

}
