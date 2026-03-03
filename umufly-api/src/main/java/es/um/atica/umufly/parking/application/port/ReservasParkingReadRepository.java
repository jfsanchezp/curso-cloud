package es.um.atica.umufly.parking.application.port;

import java.util.UUID;

import org.springframework.data.domain.Page;

import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

public interface ReservasParkingReadRepository {

	ReservaParking findParkingById( DocumentoIdentidad documentoIdentidad, UUID idParking );

	Page<ReservaParking> findParking( DocumentoIdentidad documentoIdentidad, int pagina, int tamanioPagina );

	UUID findIdFormalizadaByParkingById(UUID idParking);

}
