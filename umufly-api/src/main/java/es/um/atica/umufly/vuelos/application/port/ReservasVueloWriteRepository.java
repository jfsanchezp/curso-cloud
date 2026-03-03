package es.um.atica.umufly.vuelos.application.port;

import java.util.UUID;

import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public interface ReservasVueloWriteRepository {

	/**
	 * M�todo que persiste una reserva de vuelo.
	 *
	 * @param reservaVuelo
	 */
	void persistirReserva( ReservaVuelo reservaVuelo );


	/**
	 * M�todo que persiste la formalizaci�n de la reserva de vuelo.
	 *
	 * @param idReserva
	 * @param idReservaFormalizada
	 */
	void persistirFormalizacionReserva( UUID idReserva, UUID idReservaFormalizada );

	/**
	 * M�todo que cancela una reserva a traves de su id.
	 *
	 * @param idReserva
	 * @return
	 */
	void cancelReserva( UUID idReserva );

}
