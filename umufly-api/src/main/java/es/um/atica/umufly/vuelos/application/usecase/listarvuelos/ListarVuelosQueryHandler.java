package es.um.atica.umufly.vuelos.application.usecase.listarvuelos;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.application.mapper.ApplicationMapper;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosReadRepository;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class ListarVuelosQueryHandler implements QueryHandler<Page<VueloAmpliadoDTO>, ListarVuelosQuery> {

	private final VuelosReadRepository vuelosRepository;
	private final ReservasVueloReadRepository reservasVueloReadRepository;


	private ListarVuelosQueryHandler( VuelosReadRepository vuelosRepository, ReservasVueloReadRepository reservasVueloReadRepository ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloReadRepository = reservasVueloReadRepository;
	}

	@Override
	public Page<VueloAmpliadoDTO> handle( ListarVuelosQuery query ) throws Exception {
		Page<Vuelo> vuelos = vuelosRepository.findVuelos( query.getPage(), query.getSize() );
		Map<UUID, UUID> vuelosReserva = query.getUsuario() != null ? reservasVueloReadRepository.findReservasIdByVueloIdAndPasajero( query.getUsuario(), vuelos.map( Vuelo::getId ).getContent() ) : Collections.emptyMap();

		return vuelos.map( v -> ApplicationMapper.vueloToDTO( v, vuelosReserva.get( v.getId() ) ) );
	}

}
