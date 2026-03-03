package es.um.atica.umufly.vuelos.application.usecase.listarreservas;

import org.springframework.data.domain.Page;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.domain.model.ReservaVuelo;

public class ListarReservasQuery extends Query<Page<ReservaVuelo>> {

	private DocumentoIdentidad usuario;
	private Integer page;
	private Integer size;

	private ListarReservasQuery( DocumentoIdentidad usuario, Integer page, Integer size ) {
		this.usuario = usuario;
		this.page = page;
		this.size = size;
	}

	public static ListarReservasQuery of( DocumentoIdentidad usuario, Integer page, Integer size ) {
		return new ListarReservasQuery( usuario, page, size );
	}

	public DocumentoIdentidad getUsuario() {
		return usuario;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getSize() {
		return size;
	}

}
