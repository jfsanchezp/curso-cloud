package es.um.atica.umufly.parking.adaptors.providers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.MuchoVueloClientParking;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.dto.ReservaParkingProviderDTO;
import es.um.atica.umufly.parking.adaptors.providers.muchovuelo.mapper.MuchoVueloMapper;
import es.um.atica.umufly.parking.application.port.ReservasParkingWritePort;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@Component
public class ReservasParkingAdapter implements ReservasParkingWritePort {

	private final MuchoVueloClientParking muchoVueloClient;

	public ReservasParkingAdapter( MuchoVueloClientParking muchoVueloClient ) {
		this.muchoVueloClient = muchoVueloClient;
	}

	@Override
	public UUID formalizarParking( ReservaParking reserva ) {
		ReservaParkingProviderDTO reservaParkingMuchoVuelo = muchoVueloClient.crearParking( MuchoVueloMapper.reservaToDTO( reserva ) );
		return reservaParkingMuchoVuelo.getId();
	}

	@Override
	public void cancelarParking( DocumentoIdentidad documentoIdentidadTitular, UUID idParking ) {
		muchoVueloClient.cancelarParking( documentoIdentidadTitular, idParking );
	}

}
