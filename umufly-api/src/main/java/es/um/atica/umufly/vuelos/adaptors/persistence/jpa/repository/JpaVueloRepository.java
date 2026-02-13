package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.VueloExtViewEntity;

public interface JpaVueloRepository extends JpaRepository<VueloExtViewEntity, String> {

	@Query( value = "SELECT COUNT(1) FROM FORMACION_TICARUM.VW_ALL_VUELO_PASAJERO WHERE ID_VUELO = :idVuelo", nativeQuery = true )
	int countPasajerosByIdVuelo( @Param( "idVuelo" ) String idVuelo );

}
