package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoDocumentoEnum;

public interface JpaReservaVueloRepository extends JpaRepository<ReservaVueloEntity, String> {

	@Query( value = """
			SELECT COUNT(1)
			FROM FORMACION_TICARUM.VW_ALL_VUELO_PASAJERO
			WHERE ID_VUELO = :idVuelo
			AND TIPO_DOCUMENTO_PASAJERO = :tipoDocumento
			AND NUMERO_DOCUMENTO_PASAJERO = :numeroDocumento
			""", nativeQuery = true )
	int countReservasByIdVueloAndPasajero( @Param( "idVuelo" ) String idVuelo, @Param( "tipoDocumento" ) String tipoDocumento, @Param( "numeroDocumento" ) String numeroDocumento );

	List<ReservaVueloEntity> findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloInAndEstadoReservaIn( TipoDocumentoEnum tipoDocumento, String numeroDocumento, List<String> idsVuelo, List<EstadoReservaVueloEnum> estados );
}
