package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloVigentePasajeroViewEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoDocumentoEnum;

public interface JpaReservaVueloVigentePasajeroRepository extends JpaRepository<ReservaVueloVigentePasajeroViewEntity, String> {

	List<ReservaVueloVigentePasajeroViewEntity> findByNumeroDocumentoAndTipoDocumentoAndIdVueloIn( String numeroDocumento, TipoDocumentoEnum tipoDocumento, Iterable<String> idsVuelo );
}
