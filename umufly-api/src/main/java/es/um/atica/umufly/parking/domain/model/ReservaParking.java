package es.um.atica.umufly.parking.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

// Agregado raíz
public class ReservaParking {

	private UUID id;
	private DocumentoIdentidad identificadorCliente;
	private Estacionamiento estacionamiento;
	private Periodo periodoEstacionamiento;
	private Importe importe;
	private LocalDateTime fechaReserva;
	private EstadoParking estado;

	private ReservaParking( UUID id, DocumentoIdentidad identificadorCliente, Estacionamiento estacionamiento, Periodo periodoEstacionamiento, Importe importe, LocalDateTime fechaReserva, EstadoParking estado ) {
		this.id = id;
		this.identificadorCliente = identificadorCliente;
		this.estacionamiento = estacionamiento;
		this.periodoEstacionamiento = periodoEstacionamiento;
		this.importe = importe;
		this.fechaReserva = fechaReserva;
		this.estado = estado;
	}

	public static ReservaParking of( UUID id, DocumentoIdentidad identificadorCliente, Estacionamiento estacionamiento, Periodo periodoEstacionamiento, Importe importe, LocalDateTime fechaReserva, EstadoParking estado ) {
		if ( id == null ) {
			throw new IllegalArgumentException( "El id de la reserva no puede ser nulo" );
		}
		if ( identificadorCliente == null ) {
			throw new IllegalArgumentException( "El cliente de la reserva no puede ser nulo" );
		}
		if ( estacionamiento == null ) {
			throw new IllegalArgumentException( "El estacionamiento de la reserva no puede ser nulo" );
		}
		if ( periodoEstacionamiento == null ) {
			throw new IllegalArgumentException( "El periodo de estacionamiento de la reserva no puede ser nulo" );
		}
		if ( estado == null ) {
			throw new IllegalArgumentException( "El estado de la reserva no puede ser nulo" );
		}

		return new ReservaParking( id, identificadorCliente, estacionamiento, periodoEstacionamiento, importe, fechaReserva, estado );
	}

	public UUID getId() {
		return id;
	}

	public DocumentoIdentidad getIdentificadorCliente() {
		return identificadorCliente;
	}

	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
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

	public EstadoParking getEstado() {
		return estado;
	}

	public static ReservaParking solicitarParking( DocumentoIdentidad identificadorPasajero, Periodo periodoEstacionamiento, LocalDateTime fechaReserva ) {
		if ( periodoEstacionamiento.inicio().isBefore( fechaReserva ) ) {
			throw new IllegalArgumentException( "No se puede realizar una reserva para un periodo anterior a la fecha actual" );
		}
		// Estacionamiento estacionamiento = crearEstacionamiento( periodoEstacionamiento );
		// Importe importe = new Importe( calcularImporte( identificadorPasajero, estacionamiento, periodoEstacionamiento ) );
		return of( UUID.randomUUID(), identificadorPasajero, null, periodoEstacionamiento, null, fechaReserva, EstadoParking.PENDIENTE );
	}

	public void crearEstacionamiento( Periodo periodoEstacionamiento ) {
		long dias = ChronoUnit.DAYS.between( periodoEstacionamiento.inicio(), periodoEstacionamiento.fin() );
		// TODO: Es correcto obtener asi el precio del estacionamiento? La idea es que esta información la coja de la vista
		// VWEXT_TIPO_ESTACIONAMIENTO a traves de un evento.
		if ( dias == 0 ) {
			this.estacionamiento = new Estacionamiento( TipoEstacionamiento.CORTA_DURACION, 0.02 );
		} else {
			this.estacionamiento = new Estacionamiento( TipoEstacionamiento.LARGA_DURACION, 7.0 );
		}
	}

	public void calcularImporte( DocumentoIdentidad identificadorPasajero, Estacionamiento estacionamiento, Periodo periodoEstacionamiento ) {
		double valor = 0;
		if ( TipoEstacionamiento.CORTA_DURACION.equals( estacionamiento.tipo() ) ) {
			Duration duration  = Duration.between( periodoEstacionamiento.inicio(), periodoEstacionamiento.fin() );
			long minutos = duration.toMinutes();
			valor = minutos * estacionamiento.valor();
		}else {
			long dias = ChronoUnit.DAYS.between( periodoEstacionamiento.inicio(), periodoEstacionamiento.fin() );
			valor = dias * estacionamiento.valor();
		}
		// TODO: Como obtenemos si el que solicita el parking es un pasajero de algun vuelo para aplicarle el 75%?
		// if() {
		// valor = valor * 0.75;
		// }
		this.importe = new Importe( valor );
	}

	public void formalizarParking() {
		estado = EstadoParking.ACTIVA;
	}

	public void cancelarParking(LocalDateTime now) {
		if ( now.isAfter( getPeriodoEstacionamiento().inicio() )) {
			throw new IllegalArgumentException( "La reserva no se puede cancelar porque ya se ha iniciado el periodo de reserva" );
		}
		estado = EstadoParking.CANCELADA;
	}
}