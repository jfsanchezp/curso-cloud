package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import java.util.UUID;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.VueloDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliadoDTO;
import es.um.atica.umufly.vuelos.application.usecase.vuelos.GestionarVuelosUseCase;
import es.um.atica.umufly.vuelos.domain.model.DocumentoIdentidad;

@RestController
public class VuelosEndpointV2 {

	private final GestionarVuelosUseCase gestionarVuelosUseCase;
	private final VuelosModelAssemblerV2 vuelosModelAssemblerV2;
	private final PagedResourcesAssembler<VueloAmpliadoDTO> pagedResourcesAssembler;
	private final AuthService authService;

	public VuelosEndpointV2( GestionarVuelosUseCase gestionarVuelosUseCase, VuelosModelAssemblerV2 vuelosModelAssemblerV2, PagedResourcesAssembler<VueloAmpliadoDTO> pagedResourcesAssembler, AuthService authService ) {
		this.gestionarVuelosUseCase = gestionarVuelosUseCase;
		this.vuelosModelAssemblerV2 = vuelosModelAssemblerV2;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_VUELOS )
	public CollectionModel<VueloDTO> getVuelos( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size ) {
		DocumentoIdentidad documento = authService.parseUserHeader( usuario );
		return pagedResourcesAssembler.toModel( gestionarVuelosUseCase.listarVuelos( documento, page, size ), vuelosModelAssemblerV2 );
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_VUELOS + Constants.ID_VUELOS )
	public VueloDTO getVuelo( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @PathVariable( "idVuelo" ) UUID idVuelo ) {
		return vuelosModelAssemblerV2.toModel( gestionarVuelosUseCase.obtenerVuelo( authService.parseUserHeader( usuario ), idVuelo ) );
	}

}
