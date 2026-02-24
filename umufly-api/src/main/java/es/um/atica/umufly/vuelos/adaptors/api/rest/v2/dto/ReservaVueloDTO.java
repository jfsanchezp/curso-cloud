package es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Relation( collectionRelation = "reservasVuelo", itemRelation = "reservaVuelo" )
@JsonInclude( content = Include.NON_NULL )
public class ReservaVueloDTO extends RepresentationModel<ReservaVueloDTO> {

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private UUID id;

	@Valid
	@NotNull
	private VueloDTO vuelo;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private DocumentoIdentidadDTO documentoIdentidadTitular;

	@Valid
	@NotNull
	private PasajeroDTO pasajero;

	@NotNull
	private ClaseAsientoReserva claseAsiento;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private EstadoReserva estado;

	@JsonFormat( pattern = Constants.PATTERN_FECHA_HORA )
	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private LocalDateTime fechaReserva;

	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public VueloDTO getVuelo() {
		return vuelo;
	}

	public void setVuelo( VueloDTO vuelo ) {
		this.vuelo = vuelo;
	}

	public DocumentoIdentidadDTO getDocumentoIdentidadTitular() {
		return documentoIdentidadTitular;
	}

	public void setDocumentoIdentidadTitular( DocumentoIdentidadDTO documentoIdentidadTitular ) {
		this.documentoIdentidadTitular = documentoIdentidadTitular;
	}

	public PasajeroDTO getPasajero() {
		return pasajero;
	}

	public void setPasajero( PasajeroDTO pasajero ) {
		this.pasajero = pasajero;
	}

	public ClaseAsientoReserva getClaseAsiento() {
		return claseAsiento;
	}

	public void setClaseAsiento( ClaseAsientoReserva claseAsiento ) {
		this.claseAsiento = claseAsiento;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado( EstadoReserva estado ) {
		this.estado = estado;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva( LocalDateTime fechaReserva ) {
		this.fechaReserva = fechaReserva;
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
		ReservaVueloDTO other = ( ReservaVueloDTO ) obj;
		return Objects.equals( id, other.id );
	}

}
