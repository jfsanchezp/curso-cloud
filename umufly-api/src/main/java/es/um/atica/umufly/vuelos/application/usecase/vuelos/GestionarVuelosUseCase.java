package es.um.atica.umufly.vuelos.application.usecase.vuelos;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.application.port.VuelosRepository;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class GestionarVuelosUseCase {

	private final VuelosRepository vuelosRepository;

	public GestionarVuelosUseCase( VuelosRepository vuelosRepository ) {
		this.vuelosRepository = vuelosRepository;
	}

	public Page<Vuelo> listarVuelos( int pagina, int tamanioPagina ) {
		return vuelosRepository.findVuelos( pagina, tamanioPagina );
	}

}
