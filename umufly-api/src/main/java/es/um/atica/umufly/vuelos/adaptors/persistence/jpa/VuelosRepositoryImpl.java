package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper.VueloMapper;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaVueloRepository;
import es.um.atica.umufly.vuelos.application.port.VuelosRepository;
import es.um.atica.umufly.vuelos.domain.model.Vuelo;

@Service
@Primary
public class VuelosRepositoryImpl implements VuelosRepository {

	private JpaVueloRepository jpaVueloRepository;

	public VuelosRepositoryImpl( JpaVueloRepository jpaVueloRepository ) {
		this.jpaVueloRepository = jpaVueloRepository;
	}

	@Override
	public Page<Vuelo> findVuelos( int pagina, int tamanioPagina ) {
		return jpaVueloRepository.findAll( PageRequest.of( pagina, tamanioPagina ) ).map( VueloMapper::vueloEntityToModel );
	}

}
