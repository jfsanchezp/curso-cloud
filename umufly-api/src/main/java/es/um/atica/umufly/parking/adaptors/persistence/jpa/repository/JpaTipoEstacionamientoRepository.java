package es.um.atica.umufly.parking.adaptors.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoEnum;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoViewExtEntity;

public interface JpaTipoEstacionamientoRepository extends JpaRepository<TipoEstacionamientoViewExtEntity, TipoEstacionamientoEnum> {

}
