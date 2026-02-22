package es.um.atica.umufly.vuelos.application.usecase.reservas;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.application.port.FormalizacionReservasVueloPort;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosRepository;
import es.um.atica.umufly.vuelos.domain.model.ClaseAsientoReserva;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class GestionarReservaUseCase {

	private final VuelosRepository vuelosRepository;
	private final ReservasVueloRepository reservasVueloRepository;
	private final FormalizacionReservasVueloPort formalizacionReservasVueloPort;
	private final Clock clock;

	public GestionarReservaUseCase( VuelosRepository vuelosRepository, ReservasVueloRepository reservasVueloRepository, FormalizacionReservasVueloPort formalizacionReservasVueloPort, Clock clock ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloRepository = reservasVueloRepository;
		this.formalizacionReservasVueloPort = formalizacionReservasVueloPort;
		this.clock = clock;
	}

	public ReservaVuelo creaReserva( DocumentoIdentidad documentoIdentidadTitular, UUID idVuelo, ClaseAsientoReserva claseAsiento, Pasajero pasajero ) {
		// 1. Recuperar el vuelo
		Vuelo vuelo = vuelosRepository.findVuelo( idVuelo );

		// 2. Recuperar el número de reservas del pasajero en el vuelo
		int numeroReservasPasajeroEnVuelo = reservasVueloRepository.countReservasByIdVueloAndPasajero( idVuelo, pasajero );

		// 3. Recuperar el número de plazas disponibles en el avión
		int numeroPlazasDisponiblesAvion = vuelosRepository.plazasDisponiblesEnVuelo( vuelo );

		// 4. Creamos y persistimos la reserva de vuelo
		ReservaVuelo reservaVuelo = ReservaVuelo.solicitarReserva( documentoIdentidadTitular, pasajero, vuelo, claseAsiento, LocalDateTime.now( clock ), numeroReservasPasajeroEnVuelo, numeroPlazasDisponiblesAvion );
		reservasVueloRepository.persistirReserva( reservaVuelo );

		// 5. Formalizamos la reserva llamando al backoffice para que se haga eco de la nueva reserva que acabamos de crear
		UUID idReservaFormalizada = formalizacionReservasVueloPort.formalizarReservaVuelo( reservaVuelo );
		reservaVuelo.formalizarReserva();
		reservasVueloRepository.persistirFormalizacionReserva( reservaVuelo.getId(), idReservaFormalizada );

		return reservaVuelo;
	}

	// TODO:Revisar si cancelar la reserva entera o solo la de un pasajero concreto
	public ReservaVuelo cancelarReserva( UUID idReserva ) {
		// 1. Recuperamos la reserva
		ReservaVuelo reservaVuelo = reservasVueloRepository.findReservaById( idReserva );

		// 2. Cancelamos la reserva llamando al backoffice para que se haga eco de la cancelacion
		// TODO: Falta un ORDS para esto
		// 3. Cancelamos la reserva en el fronOffice
		reservasVueloRepository.cancelReserva( reservaVuelo.getId() );

		return reservaVuelo;
	}

	public Page<ReservaVuelo> listarReservas( int pagina, int tamanioPagina ) {
		return reservasVueloRepository.findReservas( pagina, tamanioPagina );

	}

	public ReservaVuelo obtenerReserva( UUID idReserva ) {
		return reservasVueloRepository.findReservaById( idReserva );
	}

}
