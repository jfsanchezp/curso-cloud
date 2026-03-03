package es.um.atica.umufly.vuelos.application.usecase.obtenerreservas;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ObtenerReservaQueryHandler implements QueryHandler<ReservaVuelo, ObtenerReservaQuery> {

	private final ReservasVueloReadRepository reservasVueloRepository;

	public ObtenerReservaQueryHandler( ReservasVueloReadRepository reservasVueloRepository ) {
		this.reservasVueloRepository = reservasVueloRepository;
	}

	@Override
	public ReservaVuelo handle( ObtenerReservaQuery query ) throws Exception {
		return reservasVueloRepository.findReservaById( query.getDocumentoIdentidad(), query.getIdReserva() );
	}
}