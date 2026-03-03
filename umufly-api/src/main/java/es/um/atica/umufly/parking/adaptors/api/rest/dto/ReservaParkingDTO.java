package es.um.atica.umufly.parking.adaptors.api.rest.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import es.um.atica.umufly.parking.adaptors.api.rest.Constants;

@Relation( collectionRelation = "reservasParking", itemRelation = "reservaParking" )
@JsonInclude( content = Include.NON_NULL )
public class ReservaParkingDTO extends RepresentationModel<ReservaParkingDTO> {

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private UUID id;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private DocumentoIdentidadDTO documentoIdentidadTitular;

	@JsonFormat( pattern = Constants.PATTERN_FECHA_HORA )
	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private LocalDateTime fechaReserva;

	@JsonFormat( pattern = Constants.PATTERN_FECHA_HORA )
	private LocalDateTime fechaInicio;

	@JsonFormat( pattern = Constants.PATTERN_FECHA_HORA )
	private LocalDateTime fechaFin;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private EstadoParking estado;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private TipoEstacionamiento tipo;

	@JsonProperty( access = JsonProperty.Access.READ_ONLY )
	private double importe;

	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	public DocumentoIdentidadDTO getDocumentoIdentidadTitular() {
		return documentoIdentidadTitular;
	}

	public void setDocumentoIdentidadTitular( DocumentoIdentidadDTO documentoIdentidadTitular ) {
		this.documentoIdentidadTitular = documentoIdentidadTitular;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva( LocalDateTime fechaReserva ) {
		this.fechaReserva = fechaReserva;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio( LocalDateTime fechaInicio ) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin( LocalDateTime fechaFin ) {
		this.fechaFin = fechaFin;
	}

	public EstadoParking getEstado() {
		return estado;
	}

	public void setEstado( EstadoParking estado ) {
		this.estado = estado;
	}

	public TipoEstacionamiento getTipo() {
		return tipo;
	}

	public void setTipo( TipoEstacionamiento tipo ) {
		this.tipo = tipo;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte( double importe ) {
		this.importe = importe;
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
		ReservaParkingDTO other = ( ReservaParkingDTO ) obj;
		return Objects.equals( id, other.id );
	}

}
