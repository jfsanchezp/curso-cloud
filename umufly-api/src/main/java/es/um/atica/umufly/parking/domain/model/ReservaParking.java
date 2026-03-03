package es.um.atica.umufly.parking.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;

public class ReservaParking {

	private UUID id;
	private DocumentoIdentidad identificadorPasajero;
	private TipoEstacionamiento tipoEstacionamiento;
	private Periodo periodoEstacionamiento;
	private Importe importe;
	private LocalDateTime fechaReserva;
	private EstadoReserva estadoReserva;

	private ReservaParking( UUID id, DocumentoIdentidad identificadorPasajero, TipoEstacionamiento tipoEstacionamiento, Periodo periodoEstacionamiento, Importe importe, LocalDateTime fechaReserva, EstadoReserva estadoReserva ) {
		this.id = id;
		this.identificadorPasajero = identificadorPasajero;
		this.tipoEstacionamiento = tipoEstacionamiento;
		this.periodoEstacionamiento = periodoEstacionamiento;
		this.importe = importe;
		this.fechaReserva = fechaReserva;
		this.estadoReserva = estadoReserva;
	}

	public static ReservaParking of( UUID id, DocumentoIdentidad identificadorPasajero, TipoEstacionamiento tipoEstacionamiento, Periodo periodoEstacionamiento, Importe importe, LocalDateTime fechaReserva, EstadoReserva estadoReserva ) {

		if ( id == null ) {
			throw new IllegalArgumentException( "El id de la reserva no puede ser nulo" );
		}
		if ( identificadorPasajero == null ) {
			throw new IllegalArgumentException( "El pasajero de la reserva no puede ser nulo" );
		}
		if ( tipoEstacionamiento == null ) {
			throw new IllegalArgumentException( "El tipo de estacionamiento no puede ser nulo" );
		}
		if ( periodoEstacionamiento == null ) {
			throw new IllegalArgumentException( "El periodo del estacionamiento no puede ser nulo" );
		}
		if ( importe == null ) {
			throw new IllegalArgumentException( "El importe de la reserva no puede ser nulo" );
		}
		if ( fechaReserva == null ) {
			throw new IllegalArgumentException( "La fecha de la reserva no puede ser nulo" );
		}
		if ( estadoReserva == null ) {
			throw new IllegalArgumentException( "El estado de la reserva no puede ser nulo" );
		}

		return new ReservaParking( id, identificadorPasajero, tipoEstacionamiento, periodoEstacionamiento, importe, fechaReserva, estadoReserva );
	}

	public UUID getId() {
		return id;
	}

	public DocumentoIdentidad getIdentificadorPasajero() {
		return identificadorPasajero;
	}

	public TipoEstacionamiento getTipoEstacionamiento() {
		return tipoEstacionamiento;
	}

	public Periodo getPeriodoEstacionamiento() {
		return periodoEstacionamiento;
	}

	public Importe getImporte() {
		return importe;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}

}
