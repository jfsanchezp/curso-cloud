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
public class ListaVuelosQueryHandler implements QueryHandler<Page<VueloAmpliadoDTO>, ListaVuelosQuery> {

	private final VuelosReadRepository vuelosRepository;
	private final ReservasVueloReadRepository reservasVueloRepository;

	public ListaVuelosQueryHandler( VuelosReadRepository vuelosRepository, ReservasVueloReadRepository reservasVueloRepository ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloRepository = reservasVueloRepository;
	}

	@Override
	public Page<VueloAmpliadoDTO> handle( ListaVuelosQuery query ) throws Exception {
		Page<Vuelo> vuelos = vuelosRepository.findVuelos( query.getPagina(), query.getTamanioPagina() );
		Map<UUID, UUID> vuelosReserva = query.getDocumentoIdentidadPasajero() != null ? reservasVueloRepository.findReservasIdByVueloIdAndPasajero( query.getDocumentoIdentidadPasajero(), vuelos.map( Vuelo::getId ).getContent() ) : Collections.emptyMap();

		return vuelos.map( v -> ApplicationMapper.vueloToDTO( v, vuelosReserva.get( v.getId() ) ) );
	}
}
