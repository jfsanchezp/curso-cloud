package es.um.atica.umufly.vuelos.adaptors.persistence.jpa;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloVigentePasajeroViewEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper.ReservaVueloMapper;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository.JpaReservaVueloVigentePasajeroRepository;
import es.um.atica.umufly.vuelos.application.port.ReservasVueloRepository;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

@Service
@Primary
public class ReservasVueloRepositoryImpl implements ReservasVueloRepository {

	private final JpaReservaVueloVigentePasajeroRepository jpaReservaVueloVigentePasajeroRepository;

	public ReservasVueloRepositoryImpl( JpaReservaVueloVigentePasajeroRepository jpaReservaVueloVigentePasajeroRepository ) {
		this.jpaReservaVueloVigentePasajeroRepository = jpaReservaVueloVigentePasajeroRepository;
	}

	@Override
	public Map<UUID, UUID> findReservaIdByVueloIdAndPasajero( DocumentoIdentidad documentoIdentidadPasajero, List<UUID> vueloIds ) {
		List<ReservaVueloVigentePasajeroViewEntity> reservasPasajero = jpaReservaVueloVigentePasajeroRepository.findByNumeroDocumentoAndTipoDocumentoAndIdVueloIn( documentoIdentidadPasajero.identificador(),
				ReservaVueloMapper.tipoDocumentoEntityFromModel( documentoIdentidadPasajero.tipo() ), vueloIds.stream().map( UUID::toString ).toList() );
		return reservasPasajero.stream().collect( Collectors.toMap( r -> UUID.fromString( r.getIdVuelo() ), r -> UUID.fromString( r.getId() ) ) );
	}

}
