package es.um.atica.umufly.vuelos.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.events.Event;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.EstadoReserva;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public class CrearReservaVueloEvent extends Event {
	
	private final String id;
	private final String tipoIdentificadorTitular;
	private final String numeroIdentificadorTitular;
	private final String tipoIdentificadorPasajero;
	private final String numeroIdentificadorPasajero;
	private final String nombrePasajero;
	private final String primerApellidoPasajero;
	private final String segundoApellidoPasajero;
	private final String correoPasajero;
	private final String nacionalidadPasajero;
	private final String idVuelo;
	private final String clase;
	private final String estado;
	private final LocalDateTime fechaReserva;
	
	private CrearReservaVueloEvent(String id, String tipoIdentificadorTitular, String numeroIdentificadorTitular, String tipoIdentificadorPasajero,String numeroIdentificadorPasajero, String nombrePasajero, String primerApellidoPasajero,
	String segundoApellidoPasajero, String correoPasajero, String nacionalidadPasajero, String idVuelo, String clase, String estado, LocalDateTime fechaReserva) {
		this.id = id;
		this.tipoIdentificadorTitular = tipoIdentificadorTitular;
		this.numeroIdentificadorTitular = numeroIdentificadorTitular;
		this.tipoIdentificadorPasajero = tipoIdentificadorPasajero;
		this.numeroIdentificadorPasajero = numeroIdentificadorPasajero;
		this.nombrePasajero = nombrePasajero;
		this.primerApellidoPasajero = primerApellidoPasajero;
		this.segundoApellidoPasajero = segundoApellidoPasajero;
		this.correoPasajero = correoPasajero;
		this.nacionalidadPasajero = nacionalidadPasajero;
		this.idVuelo = idVuelo;
		this.clase = clase;
		this.estado = estado;
		this.fechaReserva = fechaReserva;
	}
	
	public static CrearReservaVueloEvent of (UUID id,DocumentoIdentidad identificadorTitular, Pasajero pasajero, Vuelo vuelo, ClaseAsientoReserva clase, EstadoReserva estado, LocalDateTime fechaReserva) {
		return new CrearReservaVueloEvent(id.toString(), identificadorTitular.tipo().toString(), identificadorTitular.identificador(), 
				pasajero.getIdentificador().tipo().toString(),pasajero.getIdentificador().identificador(), pasajero.getNombre().nombre(), pasajero.getNombre().primerApellido(), pasajero.getNombre().segundoApellido(), 
				pasajero.getCorreo().valor(), pasajero.getNacionalidad().valor(), vuelo.getId().toString(), clase.toString(), estado.toString(), fechaReserva);
	}

	public String getId() {
		return id;
	}
	
	public String getTipoIdentificadorTitular() {
		return tipoIdentificadorTitular;
	}

	public String getNumeroIdentificadorTitular() {
		return numeroIdentificadorTitular;
	}

	public String getTipoIdentificadorPasajero() {
		return tipoIdentificadorPasajero;
	}

	public String getNumeroIdentificadorPasajero() {
		return numeroIdentificadorPasajero;
	}

	public String getNombrePasajero() {
		return nombrePasajero;
	}

	public String getPrimerApellidoPasajero() {
		return primerApellidoPasajero;
	}

	public String getSegundoApellidoPasajero() {
		return segundoApellidoPasajero;
	}

	public String getCorreoPasajero() {
		return correoPasajero;
	}

	public String getNacionalidadPasajero() {
		return nacionalidadPasajero;
	}

	public String getIdVuelo() {
		return idVuelo;
	}

	public String getClase() {
		return clase;
	}

	public String getEstado() {
		return estado;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}
	
}
