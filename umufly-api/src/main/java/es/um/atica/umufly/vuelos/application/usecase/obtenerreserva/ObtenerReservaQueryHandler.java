package es.um.atica.umufly.vuelos.application.usecase.obtenerreserva;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ObtenerReservaQueryHandler implements QueryHandler<ReservaVuelo, ObtenerReservaQuery> {

	private final ReservasVueloReadRepository reservasVueloReadRepository;


	private ObtenerReservaQueryHandler( ReservasVueloReadRepository reservasVueloReadRepository ) {
		this.reservasVueloReadRepository = reservasVueloReadRepository;
	}

	@Override
	public ReservaVuelo handle( ObtenerReservaQuery query ) throws Exception {
		return reservasVueloReadRepository.findReservaById( query.getDocumentoIdentidad(), query.getIdReserva() );
	}

}
