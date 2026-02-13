package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;

@JsonInclude( Include.NON_NULL )
public class ItinerarioDTO {

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	@JsonFormat( pattern = Constants.PATTERN_FECHA_HORA )
	private LocalDateTime salida;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	@JsonFormat( pattern = Constants.PATTERN_FECHA_HORA )
	private LocalDateTime llegada;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private String origen;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
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
