package es.um.atica.umufly.parking.adaptors.persistence.jpa;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.EstadoParkingEnum;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.ReservaParkingEntity;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.entity.TipoEstacionamientoEnum;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.mapper.JpaPersistenceMapper;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.repository.JpaReservaParkingRepository;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.repository.JpaReservaParkingViewRepository;
import es.um.atica.umufly.parking.adaptors.persistence.jpa.repository.JpaTipoEstacionamientoRepository;
import es.um.atica.umufly.parking.application.port.ReservasParkingReadRepository;
import es.um.atica.umufly.parking.application.port.ReservasParkingWriteRepository;
import es.um.atica.umufly.parking.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.parking.domain.model.Estacionamiento;
import es.um.atica.umufly.parking.domain.model.Importe;
import es.um.atica.umufly.parking.domain.model.ReservaParking;

@Component
public class ReservasParkingPersistenceAdapter implements ReservasParkingReadRepository, ReservasParkingWriteRepository {

	private final JpaReservaParkingRepository jpaReservaParkingRepository;
	private final JpaReservaParkingViewRepository jpaReservaParkingViewRepository;
	private final JpaTipoEstacionamientoRepository jpaTipoEstacionamientoRepository;
	private final Clock clock;

	public ReservasParkingPersistenceAdapter( JpaReservaParkingRepository jpaReservaParkingRepository, JpaReservaParkingViewRepository jpaReservaParkingViewRepository, JpaTipoEstacionamientoRepository jpaTipoEstacionamientoRepository, Clock clock ) {
		this.jpaReservaParkingRepository = jpaReservaParkingRepository;
		this.jpaReservaParkingViewRepository = jpaReservaParkingViewRepository;
		this.jpaTipoEstacionamientoRepository = jpaTipoEstacionamientoRepository;
		this.clock = clock;
	}

	@Override
	public void persistirFormalizacionParking( UUID idParking, UUID idParkingFormalizada ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		ReservaParkingEntity entidad = jpaReservaParkingRepository.findById( idParking.toString() ).orElseThrow( () -> new IllegalStateException( "Reserva de parking no encontrada" ) );
		entidad.setEstadoReserva( EstadoParkingEnum.A );
		entidad.setFechaModificacion( fechaActual );
		entidad.setIdParkingFormalizada( idParkingFormalizada.toString() );
		jpaReservaParkingRepository.save( entidad );
	}

	@Override
	public ReservaParking findParkingById( DocumentoIdentidad documentoIdentidad, UUID idParking ) {
		return jpaReservaParkingViewRepository.findByIdAndTipoDocumentoClienteAndNumeroDocumentoCliente( idParking.toString(), JpaPersistenceMapper.tipoDocumentoToEntity( documentoIdentidad.tipo() ), documentoIdentidad.identificador() )
				.map( r -> JpaPersistenceMapper.reservaParkingToModel( r, jpaTipoEstacionamientoRepository.findById( r.getTipo() ).orElseGet( null ) ) ).orElseThrow( () -> new IllegalStateException( "Reserva no encontrado" ) );
	}

	@Override
	public Page<ReservaParking> findParking( DocumentoIdentidad documentoIdentidad, int pagina, int tamanioPagina ) {
		return jpaReservaParkingViewRepository.findByTipoDocumentoClienteAndNumeroDocumentoCliente( JpaPersistenceMapper.tipoDocumentoToEntity( documentoIdentidad.tipo() ), documentoIdentidad.identificador(),
				PageRequest.of( pagina, tamanioPagina ) ).map( r -> JpaPersistenceMapper.reservaParkingToModel(r, jpaTipoEstacionamientoRepository.findById( r.getTipo()).orElseGet( null )));
	}

	@Override
	public void cancelParking( UUID idParking ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		ReservaParkingEntity entidad = jpaReservaParkingRepository.findById( idParking.toString() ).orElseThrow( () -> new IllegalStateException( "Reserva de parking no encontrada" ) );
		entidad.setEstadoReserva( EstadoParkingEnum.X );
		entidad.setFechaModificacion( fechaActual );
		jpaReservaParkingRepository.save( entidad );
	}

	@Override
	public void persistirParking( ReservaParking reservaParking ) {
		LocalDateTime fechaActual = LocalDateTime.now( clock );
		jpaReservaParkingRepository.save( JpaPersistenceMapper.reservaParkingToEntity( reservaParking, fechaActual, fechaActual ) );

	}

	@Override
	public UUID findIdFormalizadaByParkingById(UUID idParking) {
		return UUID.fromString(jpaReservaParkingRepository.findById(idParking.toString()).orElseThrow(() -> new IllegalStateException( "Reserva de parking no encontrada" )).getIdParkingFormalizada());
	}

	@Override
	public void actualizarImporteParking( UUID id, Estacionamiento estacionamiento, Importe importe ) {
		ReservaParkingEntity entidad = jpaReservaParkingRepository.findById( id.toString() ).orElseThrow( () -> new IllegalStateException( "Reserva de parking no encontrada" ) );
		entidad.setTipo( TipoEstacionamientoEnum.valueOf( estacionamiento.tipo().toString() ) );
		entidad.setValorEstacionamiento( importe.valor() );
		jpaReservaParkingRepository.save( entidad );
	}


}
