package es.um.atica.umufly.parking.application.port;

import es.um.atica.umufly.parking.domain.model.TipoEstacionamiento;

public interface TipoEstacionamientoReadRepository {

	double findPrecioByTipoEstacionamiento( TipoEstacionamiento tipoEstacionamiento );

}
