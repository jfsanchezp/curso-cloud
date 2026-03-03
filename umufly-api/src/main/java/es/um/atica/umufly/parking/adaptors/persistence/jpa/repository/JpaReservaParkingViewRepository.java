package es.um.atica.umufly.parking.adaptors.persistence.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.ReservaParkingViewEntity;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoDocumentoEnum;

public interface JpaReservaParkingViewRepository extends JpaRepository<ReservaParkingViewEntity, String> {

	Page<ReservaParkingViewEntity> findByTipoDocumentoClienteAndNumeroDocumentoCliente( TipoDocumentoEnum tipoDocumento, String numeroDocumento, PageRequest pagina );

	Optional<ReservaParkingViewEntity> findByIdAndTipoDocumentoClienteAndNumeroDocumentoCliente( String idParking, TipoDocumentoEnum tipoDocumento, String numeroDocumento );

}
