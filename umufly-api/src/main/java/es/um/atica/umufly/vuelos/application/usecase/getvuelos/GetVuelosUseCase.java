package es.um.atica.umufly.vuelos.application.usecase.getvuelos;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.application.dto.VueloAmpliado;
import es.um.atica.umufly.vuelos.application.mapper.VueloMapper;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosRepository;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class GetVuelosUseCase {

	private final VuelosRepository vuelosRepository;
	private final ReservasVueloRepository reservasVueloRepository;

	public GetVuelosUseCase( VuelosRepository vuelosRepository, ReservasVueloRepository reservasVueloRepository ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloRepository = reservasVueloRepository;
	}

	public Page<VueloAmpliado> getVuelos(DocumentoIdentidad documentoIdentidadPasajero, int pagina, int tamanioPagina ) {
		Page<Vuelo> vuelos = vuelosRepository.findVuelos( pagina, tamanioPagina );
		Map<UUID, UUID> vuelosReserva = documentoIdentidadPasajero != null ? reservasVueloRepository.findReservaIdByVueloIdAndPasajero( documentoIdentidadPasajero, vuelos.map( Vuelo::getId ).getContent() ) : Collections.emptyMap();

		return vuelos.map( v -> VueloMapper.vueloModelToVueloAmpliado( v, vuelosReserva.get( v.getId() ) ) );
	}
}
