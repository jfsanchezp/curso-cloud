package es.um.atica.umufly.parking.adaptors.persistence.jpa;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.mapper.JpaPersistenceMapper;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.repository.JpaTipoEstacionamientoRepository;
import es.um.atica.umufly.parking.application.port.TipoEstacionamientoReadRepository;
import es.um.atica.umufly.parking.domain.model.TipoEstacionamiento;

@Component
public class TipoEstacionamientoPersistenceAdapter implements TipoEstacionamientoReadRepository {

	private final JpaTipoEstacionamientoRepository jpaTipoEstacionamientoRepository;

	public TipoEstacionamientoPersistenceAdapter( JpaTipoEstacionamientoRepository jpaTipoEstacionamientoRepository ) {
		this.jpaTipoEstacionamientoRepository = jpaTipoEstacionamientoRepository;
	}

	@Override
	public double findPrecioByTipoEstacionamiento( TipoEstacionamiento tipoEstacionamiento ) {
		return jpaTipoEstacionamientoRepository.findById( JpaPersistenceMapper.tipoEstacionamientoToEntity( tipoEstacionamiento ) ).map( te -> te.getPrecio() ).orElseThrow( () -> new IllegalStateException( "Tipo estacionamiento no encontrado" ) );
	}

}
