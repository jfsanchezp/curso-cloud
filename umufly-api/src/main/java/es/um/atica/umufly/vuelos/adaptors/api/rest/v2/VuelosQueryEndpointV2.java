package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import java.util.UUID;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.shared.domain.model.DocumentoIdentidad;
import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.VueloDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.application.usecase.listarvuelos.ListarVuelosQuery;
import es.um.atica.umufly.vuelos.application.usecase.listarvuelos.ListarVuelosQueryHandler;
import es.um.atica.umufly.vuelos.application.usecase.obtenervuelo.ObtenerVueloQuery;
import es.um.atica.umufly.vuelos.application.usecase.obtenervuelo.ObtenerVueloQueryHandler;

@RestController
public class VuelosQueryEndpointV2 {

	private final ListarVuelosQueryHandler listarVuelosQueryHandler;
	private final ObtenerVueloQueryHandler obtenerVueloQueryHandler;

	private final VuelosModelAssemblerV2 vuelosModelAssemblerV2;
	private final PagedResourcesAssembler<VueloAmpliadoDTO> pagedResourcesAssembler;
	private final AuthService authService;

	public VuelosQueryEndpointV2( ListarVuelosQueryHandler listarVuelosQueryHandler, ObtenerVueloQueryHandler obtenerVueloQueryHandler, VuelosModelAssemblerV2 vuelosModelAssemblerV2,
			PagedResourcesAssembler<VueloAmpliadoDTO> pagedResourcesAssembler, AuthService authService ) {
		this.listarVuelosQueryHandler = listarVuelosQueryHandler;
		this.obtenerVueloQueryHandler = obtenerVueloQueryHandler;
		this.vuelosModelAssemblerV2 = vuelosModelAssemblerV2;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_VUELOS )
	public CollectionModel<VueloDTO> getVuelos( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size )
			throws Exception {
		DocumentoIdentidad documento = authService.parseUserHeader( usuario );
		return pagedResourcesAssembler.toModel( listarVuelosQueryHandler.handle( ListarVuelosQuery.of( documento, page, size ) ), vuelosModelAssemblerV2 );
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_VUELOS + Constants.ID_VUELOS )
	public VueloDTO getVuelo( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idVuelo" ) UUID idVuelo ) throws Exception {
		return vuelosModelAssemblerV2.toModel( obtenerVueloQueryHandler.handle( ObtenerVueloQuery.of( authService.parseUserHeader( usuario ), idVuelo ) ) );
	}

}
