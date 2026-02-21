package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import java.util.UUID;

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

	@Override
	public Vuelo findVuelo( UUID idVuelo ) {
		return jpaVueloRepository.findById( idVuelo.toString() ).map( JpaPersistenceMapper::vueloToModel ).orElseThrow( () -> new IllegalStateException( "Vuelo no encontrado" ) );
	}

	@Override
	public int plazasDisponiblesEnVuelo( Vuelo vuelo ) {
		int ocupadas = jpaVueloRepository.countPasajerosByIdVuelo( vuelo.getId().toString() );
		return Math.max( vuelo.getAvion().capacidad() - ocupadas, 0 );
	}

}
