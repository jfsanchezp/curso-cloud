package es.um.atica.umufly.vuelos.application.port;

import java.util.UUID;

import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public interface FormalizacionReservasVueloPort {

	UUID formalizarReservaVuelo( ReservaVuelo reservaVuelo );

}
