package es.um.atica.umufly.parking.adaptors.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.ReservaParkingEntity;

public interface JpaReservaParkingRepository extends JpaRepository<ReservaParkingEntity, String> {

}
