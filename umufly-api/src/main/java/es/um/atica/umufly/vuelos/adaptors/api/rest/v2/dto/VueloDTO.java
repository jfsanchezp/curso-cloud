package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto;

import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

@Relation( collectionRelation = "vuelos", itemRelation = "vuelo" )
public class VueloDTO extends RepresentationModel<VueloDTO> {

	@NotNull
	private UUID id;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private ItinerarioDTO itinerario;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private TipoVuelo tipo;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private EstadoVuelo estado;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private AvionDTO avion;

	public VueloDTO( UUID id, ItinerarioDTO itinerario, TipoVuelo tipo, EstadoVuelo estado, AvionDTO avion ) {
		this.id = id;
		this.itinerario = itinerario;
		this.tipo = tipo;
		this.estado = estado;
		this.avion = avion;
	}

	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public ItinerarioDTO getItinerario() {
		return itinerario;
	}

	public void setItinerario( ItinerarioDTO itinerario ) {
		this.itinerario = itinerario;
	}

	public TipoVuelo getTipo() {
		return tipo;
	}

	public void setTipo( TipoVuelo tipo ) {
		this.tipo = tipo;
	}

	public EstadoVuelo getEstado() {
		return estado;
	}

	public void setEstado( EstadoVuelo estado ) {
		this.estado = estado;
	}

	public AvionDTO getAvion() {
		return avion;
	}

	public void setAvion( AvionDTO avion ) {
		this.avion = avion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash( id );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( !super.equals( obj ) || getClass() != obj.getClass() ) {
			return false;
		}
		VueloDTO other = ( VueloDTO ) obj;
		return Objects.equals( id, other.id );
	}

}
