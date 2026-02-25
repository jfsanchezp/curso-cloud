package es.um.atica.umufly.vuelos.adaptors.providers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.MuchoVueloClient;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto.ReservaVueloDTO;
import es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.mapper.MuchoVueloMapper;
import es.um.atica.umufly.vuelos.application.port.FormalizacionReservasVueloPort;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class FormalizacionReservasVueloAdapter implements FormalizacionReservasVueloPort {

	private final MuchoVueloClient muchoVueloClient;

	public FormalizacionReservasVueloAdapter( MuchoVueloClient muchoVueloClient ) {
		this.muchoVueloClient = muchoVueloClient;
	}

	@Override
	public UUID formalizarReservaVuelo( ReservaVuelo reservaVuelo ) {
		ReservaVueloDTO reservaVueloMuchoVuelo = muchoVueloClient.creaReservaVuelo( MuchoVueloMapper.reservaToDTO( reservaVuelo ) );
		return reservaVueloMuchoVuelo.getId();
	}

	@Override
	public void cancelarReservaVuelo( DocumentoIdentidad documentoIdentidadTitular, UUID idReserva ) {
		muchoVueloClient.cancelarReservaVuelo( MuchoVueloMapper.tipoDocumentoToDTO( documentoIdentidadTitular.tipo() ), documentoIdentidadTitular.identificador(), idReserva );
	}

}
