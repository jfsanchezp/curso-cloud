package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;

public interface JpaReservaVueloRepository extends JpaRepository<ReservaVueloEntity, String> {

	@Query( value = """
			SELECT COALESCE(
			    (
			        SELECT NUMERO_RESERVAS
			        FROM FORMACION_TICARUM.VW_ALL_VUELO_PASAJERO
			        WHERE ID_VUELO = :idVuelo
			          AND TIPO_DOCUMENTO_PASAJERO = :tipoDocumento
			          AND NUMERO_DOCUMENTO_PASAJERO = :numeroDocumento
			    ),
			    0
			) AS NUMERO_RESERVAS
			FROM DUAL
			""", nativeQuery = true )
	int countReservasByIdVueloAndPasajero( @Param( "idVuelo" ) String idVuelo, @Param( "tipoDocumento" ) String tipoDocumento, @Param( "numeroDocumento" ) String numeroDocumento );
}
