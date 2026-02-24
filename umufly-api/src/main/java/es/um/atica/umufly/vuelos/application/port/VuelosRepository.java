package es.um.atica.umufly.vuelos.application.port;

import org.springframework.data.domain.Page;

import es.um.atica.umufly.vuelos.domain.model.Vuelo;

public interface VuelosRepository {

	Page<Vuelo> findVuelos( int pagina, int tamanioPagina );

}
