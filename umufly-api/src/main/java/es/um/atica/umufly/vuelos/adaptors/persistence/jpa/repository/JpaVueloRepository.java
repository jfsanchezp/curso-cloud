package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.VueloExtViewEntity;

public interface JpaVueloRepository extends JpaRepository<VueloExtViewEntity, String> {

}
