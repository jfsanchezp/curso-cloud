package es.um.atica.umufly.parking.application.usecase.obtenerparking;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.parking.application.port.ReservasParkingReadRepository;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@Component
public class ObtenerParkingQueryHandler implements QueryHandler<ReservaParking, ObtenerParkingQuery> {

	private final ReservasParkingReadRepository reservasParkingRepository;

	public ObtenerParkingQueryHandler( ReservasParkingReadRepository reservasParkingRepository ) {
		this.reservasParkingRepository = reservasParkingRepository;
	}

	@Override
	public ReservaParking handle( ObtenerParkingQuery query ) throws Exception {
		return reservasParkingRepository.findParkingById( query.getDocumentoIdentidad(), query.getIdParking() );
	}
}