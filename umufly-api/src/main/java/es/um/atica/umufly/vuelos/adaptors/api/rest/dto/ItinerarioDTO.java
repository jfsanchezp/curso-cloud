package es.um.atica.umufly.vuelos.adaptors.api.rest.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class ItinerarioDTO {

	private LocalDateTime salida;
	private LocalDateTime llegada;
	private String origen;
	private String destino;

	public ItinerarioDTO( LocalDateTime salida, LocalDateTime llegada, String origen, String destino ) {
		this.salida = salida;
		this.llegada = llegada;
		this.origen = origen;
		this.destino = destino;
	}

	public LocalDateTime getSalida() {
		return salida;
	}

	public void setSalida( LocalDateTime salida ) {
		this.salida = salida;
	}

	public LocalDateTime getLlegada() {
		return llegada;
	}

	public void setLlegada( LocalDateTime llegada ) {
		this.llegada = llegada;
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

}
