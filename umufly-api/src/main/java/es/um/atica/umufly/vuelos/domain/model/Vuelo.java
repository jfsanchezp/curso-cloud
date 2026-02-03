package es.um.atica.umufly.vuelos.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Vuelo {

	private UUID id;
	private Itinerario itinerario;
	private TipoVuelo tipo;
	private EstadoVuelo estado;
	private Avion avion;

	private Vuelo( UUID id, Itinerario itinerario, TipoVuelo tipo, EstadoVuelo estado, Avion avion ) {
		this.id = id;
		this.itinerario = itinerario;
		this.tipo = tipo;
		this.estado = estado;
		this.avion = avion;
	}

	public static Vuelo of( UUID id, Itinerario itinerario, TipoVuelo tipo, EstadoVuelo estado, Avion avion ) {
		if ( id == null ) {
			throw new IllegalArgumentException( "El id del vuelo no puede ser nulo" );
		}
		if ( itinerario == null ) {
			throw new IllegalArgumentException( "El itinerario del vuelo no puede ser nulo" );
		}
		if ( tipo == null ) {
			throw new IllegalArgumentException( "El tipo del vuelo no puede ser nulo" );
		}
		if ( estado == null ) {
			throw new IllegalArgumentException( "El estado del vuelo no puede ser nulo" );
		}
		if ( avion == null ) {
			throw new IllegalArgumentException( "El avion del vuelo no puede ser nulo" );
		}
		return new Vuelo( id, itinerario, tipo, estado, avion );
	}

	public UUID getId() {
		return id;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public TipoVuelo getTipo() {
		return tipo;
	}

	public EstadoVuelo getEstado() {
		return estado;
	}

	public Avion getAvion() {
		return avion;
	}

	public boolean isIniciado( LocalDateTime fechaHoraActual ) {
		return itinerario.salida().equals( fechaHoraActual ) || itinerario.salida().isBefore( fechaHoraActual );
	}

}
