package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloVigentePasajeroViewEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper.ReservaVueloMapper;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloRepository;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloVigentePasajeroRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloRepository;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.Pasajero;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

@Component
public class ReservasVueloPersistenceAdapter implements ReservasVueloRepository {

	private final JpaReservaVueloVigentePasajeroRepository jpaReservaVueloVigentePasajeroRepository;
	private final JpaReservaVueloRepository jpaReservaVueloRepository;
	private final Clock clock;

	public ReservasVueloPersistenceAdapter( JpaReservaVueloVigentePasajeroRepository jpaReservaVueloVigentePasajeroRepository, JpaReservaVueloRepository jpaReservaVueloRepository, Clock clock ) {
		this.jpaReservaVueloVigentePasajeroRepository = jpaReservaVueloVigentePasajeroRepository;
		this.jpaReservaVueloRepository = jpaReservaVueloRepository;
		this.clock = clock;
	}

	@Override
	public Map<UUID, UUID> findReservaIdByVueloIdAndPasajero( DocumentoIdentidad documentoIdentidadPasajero, List<UUID> vueloIds ) {
		List<ReservaVueloVigentePasajeroViewEntity> reservasPasajero = jpaReservaVueloVigentePasajeroRepository.findByNumeroDocumentoAndTipoDocumentoAndIdVueloIn( documentoIdentidadPasajero.identificador(),
				ReservaVueloMapper.tipoDocumentoEntityFromModel( documentoIdentidadPasajero.tipo() ), vueloIds.stream().map( UUID::toString ).toList() );
		return reservasPasajero.stream().collect( Collectors.toMap( r -> UUID.fromString( r.getIdVuelo() ), r -> UUID.fromString( r.getId() ) ) );
	}

	@Override
	public int countReservasByIdVueloAndPasajero( UUID idVuelo, Pasajero pasajero ) {
		return jpaReservaVueloRepository.countReservasByIdVueloAndPasajero( idVuelo.toString(), ReservaVueloMapper.tipoDocumentoEntityFromModel( pasajero.getIdentificador().tipo() ).toString(), pasajero.getIdentificador().identificador() );
	}

	@Override
	public void persistirReserva( ReservaVuelo reservaVuelo ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		jpaReservaVueloRepository.save( ReservaVueloMapper.reservaVueloModelToEntity( reservaVuelo, fechaActual, fechaActual ) );

	}

	@Override
	public void persistirFormalizacionReserva( UUID idReserva, UUID idReservaFormalizada ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		ReservaVueloEntity entidad = jpaReservaVueloRepository.findById( idReserva.toString() ).orElseThrow();
		entidad.setEstadoReserva( EstadoReservaVueloEnum.A );
		entidad.setFechaModificacion( fechaActual );
		entidad.setFechaFormalizacion( fechaActual );
		entidad.setIdReservaFormalizada( idReservaFormalizada.toString() );
		jpaReservaVueloRepository.save( entidad );
	}

}
