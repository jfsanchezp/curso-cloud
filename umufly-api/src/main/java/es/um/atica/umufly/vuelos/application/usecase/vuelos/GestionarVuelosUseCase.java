package es.um.atica.umufly.vuelos.application.usecase.vuelos;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.application.mapper.ApplicationMapper;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosRepository;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class GestionarVuelosUseCase {

	private final VuelosRepository vuelosRepository;
	private final ReservasVueloRepository reservasVueloRepository;

	public GestionarVuelosUseCase( VuelosRepository vuelosRepository, ReservasVueloRepository reservasVueloRepository ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloRepository = reservasVueloRepository;
	}

	public Page<VueloAmpliadoDTO> listarVuelos(DocumentoIdentidad documentoIdentidadPasajero, int pagina, int tamanioPagina ) {
		Page<Vuelo> vuelos = vuelosRepository.findVuelos( pagina, tamanioPagina );
		Map<UUID, UUID> vuelosReserva = documentoIdentidadPasajero != null ? reservasVueloRepository.findReservasIdByVueloIdAndPasajero( documentoIdentidadPasajero, vuelos.map( Vuelo::getId ).getContent() ) : Collections.emptyMap();

		return vuelos.map( v -> ApplicationMapper.vueloToDTO( v, vuelosReserva.get( v.getId() ) ) );
	}

	public VueloAmpliadoDTO obtenerVuelo( DocumentoIdentidad documentoIdentidadPasajero, UUID idVuelo ) {
		Vuelo vuelo = vuelosRepository.findVuelo( idVuelo );
		UUID vueloReserva = documentoIdentidadPasajero != null ? reservasVueloRepository.findReservaIdByVueloIdAndPasajero( documentoIdentidadPasajero, vuelo.getId() ) : null;

		return ApplicationMapper.vueloToDTO( vuelo, vueloReserva );
	}
}
