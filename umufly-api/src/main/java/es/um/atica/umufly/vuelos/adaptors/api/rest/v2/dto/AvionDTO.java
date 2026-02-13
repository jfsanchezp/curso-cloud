package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude( Include.NON_NULL )
public class AvionDTO {

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private Integer capacidad;

	public AvionDTO( Integer capacidad ) {
		this.capacidad = capacidad;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad( Integer capacidad ) {
		this.capacidad = capacidad;
	}

}
