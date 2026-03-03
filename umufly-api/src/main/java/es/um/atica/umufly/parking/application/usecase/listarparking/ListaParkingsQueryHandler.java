package es.um.atica.umufly.parking.application.usecase.listarparking;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.parking.application.port.ReservasParkingReadRepository;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@Component
public class ListaParkingsQueryHandler implements QueryHandler<Page<ReservaParking>, ListaParkingsQuery> {

	private final ReservasParkingReadRepository reservasParkingRepository;

	public ListaParkingsQueryHandler( ReservasParkingReadRepository reservasParkingRepository ) {
		this.reservasParkingRepository = reservasParkingRepository;
	}

	@Override
	public Page<ReservaParking> handle( ListaParkingsQuery query ) throws Exception {
		return reservasParkingRepository.findParking( query.getDocumentoIdentidad(), query.getPagina(), query.getTamanioPagina() );
	}
}
