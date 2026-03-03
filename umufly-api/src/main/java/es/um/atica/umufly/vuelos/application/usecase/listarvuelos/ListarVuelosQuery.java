package es.um.atica.umufly.vuelos.application.usecase.listarvuelos;

import org.springframework.data.domain.Page;

import es.um.atica.fundewebjs.umubus.domain.cqrs.Query;
import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;

public class ListarVuelosQuery extends Query<Page<VueloAmpliadoDTO>> {

	private DocumentoIdentidad usuario;
	private Integer page;
	private Integer size;

	private ListarVuelosQuery( DocumentoIdentidad usuario, Integer page, Integer size ) {
		this.usuario = usuario;
		this.page = page;
		this.size = size;
	}

	public static ListarVuelosQuery of( DocumentoIdentidad usuario, Integer page, Integer size ) {
		return new ListarVuelosQuery( usuario, page, size );
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
