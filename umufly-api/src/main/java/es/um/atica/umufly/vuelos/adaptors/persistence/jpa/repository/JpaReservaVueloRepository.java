package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.ReservaVueloEntity;

public interface JpaReservaVueloRepository extends JpaRepository<ReservaVueloEntity, String> {

}
