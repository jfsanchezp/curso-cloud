package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloViewEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper.JpaPersistenceMapper;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloRepository;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloViewRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloRepository;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ReservasVueloPersistenceAdapter implements ReservasVueloRepository {

	private final JpaReservaVueloRepository jpaReservaVueloRepository;
	private final JpaReservaVueloViewRepository jpaReservaVueloViewRepository;
	private final Clock clock;

	public ReservasVueloPersistenceAdapter( JpaReservaVueloRepository jpaReservaVueloRepository, JpaReservaVueloViewRepository jpaReservaVueloViewRepository, Clock clock ) {
		this.jpaReservaVueloRepository = jpaReservaVueloRepository;
		this.jpaReservaVueloViewRepository = jpaReservaVueloViewRepository;
		this.clock = clock;
	}

	@Override
	public Map<UUID, UUID> findReservasIdByVueloIdAndPasajero( DocumentoIdentidad documentoIdentidadPasajero, List<UUID> vueloIds ) {
		if ( vueloIds.isEmpty() ) {
			return Collections.emptyMap();
		}

		List<ReservaVueloViewEntity> reservasVuelo = jpaReservaVueloViewRepository.findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloInAndEstadoReservaIn( JpaPersistenceMapper.tipoDocumentoToEntity( documentoIdentidadPasajero.tipo() ),
				documentoIdentidadPasajero.identificador(), vueloIds.stream().map( UUID::toString ).toList(), Arrays.asList( EstadoReservaVueloEnum.P, EstadoReservaVueloEnum.A ) );
		return reservasVuelo.stream().collect( Collectors.toMap( r -> UUID.fromString( r.getIdVuelo() ), r -> UUID.fromString( r.getId() ) ) );
	}

	@Override
	public UUID findReservaIdByVueloIdAndPasajero( DocumentoIdentidad documentoIdentidadPasajero, UUID vueloId ) {
		ReservaVueloViewEntity reservasPasajero = jpaReservaVueloViewRepository.findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloAndEstadoReservaIn( JpaPersistenceMapper.tipoDocumentoToEntity( documentoIdentidadPasajero.tipo() ),
				documentoIdentidadPasajero.identificador(),
				vueloId.toString(), Arrays.asList( EstadoReservaVueloEnum.P, EstadoReservaVueloEnum.A ) );
		return UUID.fromString( reservasPasajero.getIdVuelo() );
	}

	@Override
	public int countReservasByIdVueloAndPasajero( UUID idVuelo, Pasajero pasajero ) {
		return jpaReservaVueloViewRepository.countReservasByIdVueloAndPasajero( idVuelo.toString(), JpaPersistenceMapper.tipoDocumentoToEntity( pasajero.getIdentificador().tipo() ).toString(), pasajero.getIdentificador().identificador() );
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

}
