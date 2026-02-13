package es.um.atica.umufly.vuelos.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import es.um.atica.umufly.vuelos.domain.exception.LimiteReservasPorPasajeroEnVueloSuperadoException;
import es.um.atica.umufly.vuelos.domain.exception.VueloIniciadoException;
import es.um.atica.umufly.vuelos.domain.exception.VueloNoReservableException;
import es.um.atica.umufly.vuelos.domain.exception.VueloSinPlazasException;

// Agregado raíz
public class ReservaVuelo {

	private static final int MAX_RESERVAS_POR_PASAJERO_EN_VUELO = 1;

	private UUID id;
	private DocumentoIdentidad identificadorTitular;
	private Pasajero pasajero;
	private Vuelo vuelo;
	private ClaseAsientoReserva clase;
	private EstadoReserva estado;
	private LocalDateTime fechaReserva;

	private ReservaVuelo( UUID id, DocumentoIdentidad identificadorTitular, Pasajero pasajero, Vuelo vuelo, ClaseAsientoReserva clase, LocalDateTime fechaReserva, EstadoReserva estado ) {
		this.id = id;
		this.identificadorTitular = identificadorTitular;
		this.pasajero = pasajero;
		this.vuelo = vuelo;
		this.clase = clase;
		this.fechaReserva = fechaReserva;
		this.estado = estado;
	}

	public static ReservaVuelo of( UUID id, DocumentoIdentidad identificadorTitular, Pasajero pasajero, Vuelo vuelo, ClaseAsientoReserva clase, LocalDateTime fechaReserva, EstadoReserva estado ) {
		if ( id == null ) {
			throw new IllegalArgumentException( "El id de la reserva no puede ser nulo" );
		}
		if ( identificadorTitular == null ) {
			throw new IllegalArgumentException( "El titular de la reserva no puede ser nulo" );
		}
		if ( pasajero == null ) {
			throw new IllegalArgumentException( "El pasajero de la reserva no puede ser nulo" );
		}
		if ( vuelo == null ) {
			throw new IllegalArgumentException( "El vuelo de la reserva no puede ser nulo" );
		}
		if ( clase == null ) {
			throw new IllegalArgumentException( "La clase del asiento de la reserva no puede ser nulo" );
		}
		if ( estado == null ) {
			throw new IllegalArgumentException( "El estado de la reserva no puede ser nulo" );
		}

		return new ReservaVuelo( id, identificadorTitular, pasajero, vuelo, clase, fechaReserva, estado );
	}

	public UUID getId() {
		return id;
	}

	public DocumentoIdentidad getIdentificadorTitular() {
		return identificadorTitular;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public ClaseAsientoReserva getClase() {
		return clase;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	// Acciones sobre la reserva
	/**
	 * Método para crear una reserva de vuelo. Las restricciones que se aplicaran para crear una reserva de vuelo son las
	 * siguientes:
	 * <ol>
	 * <li>No se puede reservar un vuelo CANCELADO o COMPLETADO.</li>
	 * <li>No se puede reservar un vuelo si el pasajero ya tiene una reserva en ese vuelo.</li>
	 * <li>No se puede reservar un vuelo si no quedan plazas en el avión.</li>
	 * </ol>
	 *
	 * @param identificadorTitular
	 * @param pasajero
	 * @param vuelo
	 * @param clase
	 * @param fechaReserva
	 * @param numeroReservasPasajeroEnVuelo
	 * @param numeroPlazasDisponiblesEnAvion
	 * @return
	 */
	public static ReservaVuelo solicitarReserva( DocumentoIdentidad identificadorTitular, Pasajero pasajero, Vuelo vuelo, ClaseAsientoReserva clase, LocalDateTime fechaReserva, int numeroReservasPasajeroEnVuelo, int numeroPlazasDisponiblesEnAvion ) {
		if ( numeroReservasPasajeroEnVuelo >= MAX_RESERVAS_POR_PASAJERO_EN_VUELO ) {
			throw new LimiteReservasPorPasajeroEnVueloSuperadoException( "Sólo puede haber una reserva por pasajero en un vuelo" );
		}
		if ( EstadoVuelo.CANCELADO.equals( vuelo.getEstado() ) || EstadoVuelo.COMPLETADO.equals( vuelo.getEstado() ) ) {
			throw new VueloNoReservableException( "El vuelo se encuentra completado o cancelado" );
		}
		if ( vuelo.isIniciado( fechaReserva ) ) {
			throw new VueloIniciadoException( "El vuelo se encuentra iniciado" );
		}
		if ( numeroPlazasDisponiblesEnAvion <= 0 ) {
			throw new VueloSinPlazasException( "No hay plazas disponibles en el avión" );
		}
		return of( UUID.randomUUID(), identificadorTitular, pasajero, vuelo, clase, fechaReserva, EstadoReserva.PENDIENTE );
	}

	/**
	 * Método que formaliza una reserva de vuelo. Añadirá la fecha en la que se ha formalizado la reserva de vuelo y
	 * cambiará el estado a ACTIVA.
	 *
	 * @param fechaReserva
	 */
	public void formalizarReserva() {
		estado = EstadoReserva.ACTIVA;
	}

	/**
	 * Método para cancelar una reserva de vuelo. Las restricciones que se aplicaran para cancelar una reserva de vuelo son
	 * las siguientes:
	 * <ol>
	 * <li>Solo se pueden cancelar una reserva que se encuentre activa.</li>
	 * <li>No se puede cancelar una reserva si el vuelo ha sido iniciado.</li>
	 * </ol>
	 *
	 * @param fechaHoraActual
	 */
	public void cancelarReserva( LocalDateTime fechaHoraActual ) {
		// TODO: Implementar operación
	}

}
