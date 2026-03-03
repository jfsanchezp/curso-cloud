package es.um.atica.umufly.vuelos.application.usecase.listarreservas;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ListaReservasQueryHandler implements QueryHandler<Page<ReservaVuelo>, ListaReservasQuery> {

	private final ReservasVueloReadRepository reservasVueloRepository;

	public ListaReservasQueryHandler( ReservasVueloReadRepository reservasVueloRepository ) {
		this.reservasVueloRepository = reservasVueloRepository;
	}

	@Override
	public Page<ReservaVuelo> handle( ListaReservasQuery query ) throws Exception {
		return reservasVueloRepository.findReservas( query.getDocumentoIdentidad(), query.getPagina(), query.getTamanioPagina() );
	}
}
