package es.um.atica.umufly.vuelos.application.port;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public interface ReservasVueloRepository {

	/**
	 * Obtiene las reservas asociadas a un pasajero para un conjunto de vuelos.
	 *
	 * @param documentoIdentidadPasajero
	 *                                   documento de identidad del pasajero
	 * @param vueloIds
	 *                                   identificadores de los vuelos a consultar
	 * @return mapa cuya clave es el identificador del vuelo y cuyo valor es el identificador de la reserva asociada a dicho
	 *         vuelo; solo se incluyen los vuelos para los que el pasajero tiene una reserva activa
	 */
	Map<UUID, UUID> findReservaIdByVueloIdAndPasajero( DocumentoIdentidad documentoIdentidadPasajero, List<UUID> vueloIds );

	/**
	 * Método que cuenta las reservas de vuelo que tiene un pasajero en un vuelo concreto.
	 *
	 * @param idVuelo
	 * @param pasajero
	 * @return
	 */
	int countReservasByIdVueloAndPasajero( UUID idVuelo, Pasajero pasajero );

	/**
	 * Método que persiste una reserva de vuelo.
	 *
	 * @param reservaVuelo
	 */
	void persistirReserva( ReservaVuelo reservaVuelo );


	/**
	 * Método que persiste la formalización de la reserva de vuelo.
	 *
	 * @param idReserva
	 * @param idReservaFormalizada
	 */
	void persistirFormalizacionReserva( UUID idReserva, UUID idReservaFormalizada );

}
