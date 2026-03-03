package es.um.atica.umufly.vuelos.application.usecase.obtenervuelo;

import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.fundewebjs.umubus.domain.cqrs.QueryHandler;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.application.mapper.ApplicationMapper;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloReadRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosReadRepository;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class ObtenerVueloQueryHandler implements QueryHandler<VueloAmpliadoDTO, ObtenerVueloQuery> {

	private final VuelosReadRepository vuelosRepository;
	private final ReservasVueloReadRepository reservasVueloRepository;


	private ObtenerVueloQueryHandler( VuelosReadRepository vuelosRepository, ReservasVueloReadRepository reservasVueloRepository ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloRepository = reservasVueloRepository;
	}

	@Override
	public VueloAmpliadoDTO handle( ObtenerVueloQuery query ) throws Exception {
		Vuelo vuelo = vuelosRepository.findVuelo( query.getIdVuelo() );
		UUID vueloReserva = query.getDocumentoIdentidad() != null ? reservasVueloRepository.findReservaIdByVueloIdAndPasajero( query.getDocumentoIdentidad(), vuelo.getId() ) : null;

		return ApplicationMapper.vueloToDTO( vuelo, vueloReserva );
	}

}
