package es.um.atica.umufly.vuelos.application.usecase.listarvuelos;

import org.springframework.data.domain.Page;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

public class ListaVuelosQuery extends Query<Page<VueloAmpliadoDTO>> {

	private final DocumentoIdentidad documentoIdentidadPasajero;
	private final Integer pagina;
	private final Integer tamanioPagina;

	private ListaVuelosQuery( DocumentoIdentidad documentoIdentidadPasajero, int pagina, int tamanioPagina ) {
		this.documentoIdentidadPasajero = documentoIdentidadPasajero;
		this.pagina = pagina;
		this.tamanioPagina = tamanioPagina;
	}

	public static ListaVuelosQuery of( DocumentoIdentidad documentoIdentidadPasajero, int pagina, int tamanioPagina ) {
		return new ListaVuelosQuery( documentoIdentidadPasajero, pagina, tamanioPagina );
	}

	public DocumentoIdentidad getDocumentoIdentidadPasajero() {
		return documentoIdentidadPasajero;
	}

	public Integer getPagina() {
		return pagina;
	}

	public Integer getTamanioPagina() {
		return tamanioPagina;
	}
}
