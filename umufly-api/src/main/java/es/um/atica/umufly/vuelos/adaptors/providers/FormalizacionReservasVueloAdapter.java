package es.um.atica.umufly.vuelos.adaptors.providers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.MuchoVueloClient;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.mapper.ReservaVueloMapper;
import es.um.atica.umufly.vuelos.application.port.FormalizacionReservasVueloPort;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class FormalizacionReservasVueloAdapter implements FormalizacionReservasVueloPort {

	private final MuchoVueloClient muchoVueloClient;

	public FormalizacionReservasVueloAdapter( MuchoVueloClient muchoVueloClient ) {
		this.muchoVueloClient = muchoVueloClient;
	}

	@Override
	public UUID formalizarReservaVuelo( ReservaVuelo reservaVuelo ) {
		ReservaVueloDTO reservaVueloMuchoVuelo = muchoVueloClient.creaReservaVuelo( ReservaVueloMapper.reservaVueloModelToDTO( reservaVuelo ) );
		return reservaVueloMuchoVuelo.getId();
	}

}
