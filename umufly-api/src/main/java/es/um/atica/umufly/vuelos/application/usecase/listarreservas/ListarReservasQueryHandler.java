package es.um.atica.umufly.vuelos.application.usecase.listarreservas;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ListarReservasQueryHandler implements QueryHandler<Page<ReservaVuelo>, ListarReservasQuery> {

	private final ReservasVueloReadRepository reservasVueloReadRepository;

	private ListarReservasQueryHandler( ReservasVueloReadRepository reservasVueloReadRepository ) {
		this.reservasVueloReadRepository = reservasVueloReadRepository;
	}

	@Override
	public Page<ReservaVuelo> handle( ListarReservasQuery query ) throws Exception {
		return reservasVueloReadRepository.findReservas( query.getUsuario(), query.getPage(), query.getSize() );
	}

}
