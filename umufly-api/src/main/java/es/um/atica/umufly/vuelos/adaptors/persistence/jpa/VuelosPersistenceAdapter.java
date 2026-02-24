package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper.JpaPersistenceMapper;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaVueloRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosRepository;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Component
public class VuelosPersistenceAdapter implements VuelosRepository {

	private final JpaVueloRepository jpaVueloRepository;

	public VuelosPersistenceAdapter( JpaVueloRepository jpaVueloRepository ) {
		this.jpaVueloRepository = jpaVueloRepository;
	}

	@Override
	public Page<Vuelo> findVuelos( int pagina, int tamanioPagina ) {
		return jpaVueloRepository.findAll( PageRequest.of( pagina, tamanioPagina ) ).map( JpaPersistenceMapper::vueloToModel );
	}


}
