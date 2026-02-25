package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.EstadoReservaVueloEnum;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloViewEntity;
import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoDocumentoEnum;

public interface JpaReservaVueloViewRepository extends JpaRepository<ReservaVueloViewEntity, String> {

	@Query( value = """
			SELECT COUNT(1)
			FROM FORMACION_TICARUM.VW_ALL_VUELO_PASAJERO
			WHERE ID_VUELO = :idVuelo
			AND TIPO_DOCUMENTO_PASAJERO = :tipoDocumento
			AND NUMERO_DOCUMENTO_PASAJERO = :numeroDocumento
			""", nativeQuery = true )
	int countReservasByIdVueloAndPasajero( @Param( "idVuelo" ) String idVuelo, @Param( "tipoDocumento" ) String tipoDocumento, @Param( "numeroDocumento" ) String numeroDocumento );

	List<ReservaVueloViewEntity> findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloInAndEstadoReservaIn( TipoDocumentoEnum tipoDocumento, String numeroDocumento, List<String> idsVuelo, List<EstadoReservaVueloEnum> estados );

	ReservaVueloViewEntity findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloAndEstadoReservaIn( TipoDocumentoEnum tipoDocumento, String numeroDocumento, String idsVuelo, List<EstadoReservaVueloEnum> estados );

	@Query( """
			SELECT rv
			FROM ReservaVueloViewEntity rv
			WHERE
			    EXISTS (
			        SELECT 1
			        FROM ReservaVueloPasajeroViewEntity p
			        WHERE p.reservaVuelo = rv
			          AND p.tipoDocumento = :tipoDocumento
			          AND p.numeroDocumento = :numeroDocumento
			    )
			 OR (
			        rv.tipoDocumentoTitular = :tipoDocumento
			    AND rv.numeroDocumentoTitular = :numeroDocumento
			 )
			""" )
	Page<ReservaVueloViewEntity> findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoOrTipoDocumentoTitularAndNumeroDocumentoTitular( TipoDocumentoEnum tipoDocumento, String numeroDocumento, PageRequest pagina );

	@Query( """
			SELECT rv
			FROM ReservaVueloViewEntity rv
			WHERE rv.id = :idReserva
			  AND (
			       EXISTS (
			         SELECT 1
			         FROM ReservaVueloPasajeroViewEntity p
			         WHERE p.reservaVuelo = rv
			           AND p.tipoDocumento = :tipoDocumento
			           AND p.numeroDocumento = :numeroDocumento
			       )
			       OR (
			         rv.tipoDocumentoTitular = :tipoDocumento
			         AND rv.numeroDocumentoTitular = :numeroDocumento
			       )
			  )
			""" )
	Optional<ReservaVueloViewEntity> findByIdAndPasajerosTipoDocumentoAndPasajerosNumeroDocumentoOrTipoDocumentoTitularAndNumeroDocumentoTitular( String idReserva, TipoDocumentoEnum tipoDocumento, String numeroDocumento );
}
