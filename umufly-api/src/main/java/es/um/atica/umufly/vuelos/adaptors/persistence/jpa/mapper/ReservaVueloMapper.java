package es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper;

import es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity.TipoDocumentoEnum;
import es.um.atica.umufly.vuelos.domain.model.TipoDocumento;

public class ReservaVueloMapper {

	private ReservaVueloMapper() {
		throw new IllegalStateException( "Clase de utilidad" );
	}

	public static TipoDocumentoEnum tipoDocumentoEntityFromModel(TipoDocumento tipoDocumento) {
		return switch (tipoDocumento) {
			case NIF -> TipoDocumentoEnum.N;
			case NIE -> TipoDocumentoEnum.E;
			case PASAPORTE -> TipoDocumentoEnum.P;
			default ->
			throw new IllegalArgumentException("Unexpected value: " + tipoDocumento);
		};
	}
}
