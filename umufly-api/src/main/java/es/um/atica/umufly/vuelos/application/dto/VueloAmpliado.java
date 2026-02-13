package es.um.atica.umufly.vuelos.application.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class VueloAmpliado {

	private UUID idVuelo;
	private LocalDateTime fechaSalida;
	private LocalDateTime fechaLlegada;
	private String origen;
	private String destino;
	private String tipoVuelo;
	private String estadoVuelo;
	private Integer capacidadAvion;
	// Datos ampliados
	private Optional<UUID> idReserva;

	public UUID getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo( UUID idVuelo ) {
		this.idVuelo = idVuelo;
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

	public String getTipoVuelo() {
		return tipoVuelo;
	}

	public void setTipoVuelo( String tipoVuelo ) {
		this.tipoVuelo = tipoVuelo;
	}

	public String getEstadoVuelo() {
		return estadoVuelo;
	}

	public void setEstadoVuelo( String estadoVuelo ) {
		this.estadoVuelo = estadoVuelo;
	}

	public Integer getCapacidadAvion() {
		return capacidadAvion;
	}

	public void setCapacidadAvion( Integer capacidadAvion ) {
		this.capacidadAvion = capacidadAvion;
	}

	public Optional<UUID> getIdReserva() {
		return idReserva;
	}

	public void setIdReserva( Optional<UUID> idReserva ) {
		this.idReserva = idReserva;
	}

}
