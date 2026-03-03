package es.um.atica.umufly.parking.application.port;

import java.util.UUID;

import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

public interface ReservasParkingWritePort {

	UUID formalizarParking( ReservaParking reserva );

	void cancelarParking( DocumentoIdentidad documentoIdentidadTitular, UUID idParking );

}
