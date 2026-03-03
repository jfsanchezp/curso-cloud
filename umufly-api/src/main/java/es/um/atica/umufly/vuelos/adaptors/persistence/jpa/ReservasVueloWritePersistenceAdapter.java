package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper.JpaPersistenceMapper;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloRepository;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloViewRepository;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaVueloRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloWriteRepository;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ReservasVueloWritePersistenceAdapter implements ReservasVueloWriteRepository {

	private final JpaReservaVueloRepository jpaReservaVueloRepository;
	private final Clock clock;

	public ReservasVueloWritePersistenceAdapter( JpaReservaVueloRepository jpaReservaVueloRepository, JpaReservaVueloViewRepository jpaReservaVueloViewRepository, JpaVueloRepository jpaVueloRepository, Clock clock ) {
		this.jpaReservaVueloRepository = jpaReservaVueloRepository;
		this.clock = clock;
	}

	@Override
	public void persistirReserva( ReservaVuelo reservaVuelo ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		jpaReservaVueloRepository.save( JpaPersistenceMapper.reservaVueloToEntity( reservaVuelo, fechaActual, fechaActual ) );

	}

	@Override
	public void persistirFormalizacionReserva( UUID idReserva, UUID idReservaFormalizada ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		ReservaVueloEntity entidad = jpaReservaVueloRepository.findById( idReserva.toString() ).orElseThrow( () -> new IllegalStateException( "Reserva de vuelo no encontrada" ) );
		entidad.setEstadoReserva( EstadoReservaVueloEnum.A );
		entidad.setFechaModificacion( fechaActual );
		entidad.setFechaFormalizacion( fechaActual );
		entidad.setIdReservaFormalizada( idReservaFormalizada.toString() );
		jpaReservaVueloRepository.save( entidad );
	}

	@Override
	public void cancelReserva( UUID idReserva ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		ReservaVueloEntity entidad = jpaReservaVueloRepository.findById( idReserva.toString() ).orElseThrow( () -> new IllegalStateException( "Reserva de vuelo no encontrada" ) );
		entidad.setEstadoReserva( EstadoReservaVueloEnum.X );
		entidad.setFechaModificacion( fechaActual );
		jpaReservaVueloRepository.save( entidad );
	}

}
