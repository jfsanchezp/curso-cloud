package es.um.atica.umufly.vuelos.application.usecase.crearreservas;

import java.util.UUID;

import es.um.atica.fundewebjs.umubus.domain.cqrs.SyncCommand;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public class CrearReservaCommand extends SyncCommand<ReservaVuelo> {

	private final DocumentoIdentidad documentoIdentidadTitular;
	private final UUID idVuelo;
	private final ClaseAsientoReserva claseAsiento;
	private final Pasajero pasajero;

	private CrearReservaCommand( DocumentoIdentidad documentoIdentidadTitular, UUID idVuelo, ClaseAsientoReserva claseAsiento, Pasajero pasajero ) {
		this.documentoIdentidadTitular = documentoIdentidadTitular;
		this.idVuelo = idVuelo;
		this.claseAsiento = claseAsiento;
		this.pasajero = pasajero;
	}

	public static CrearReservaCommand of( DocumentoIdentidad documentoIdentidadTitular, UUID idVuelo, ClaseAsientoReserva claseAsiento, Pasajero pasajero ) {
		return new CrearReservaCommand( documentoIdentidadTitular, idVuelo, claseAsiento, pasajero );
	}

	public DocumentoIdentidad getDocumentoIdentidadTitular() {
		return documentoIdentidadTitular;
	}

	public UUID getIdVuelo() {
		return idVuelo;
	}

	public ClaseAsientoReserva getClaseAsiento() {
		return claseAsiento;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}
}
