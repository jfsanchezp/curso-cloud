package es.um.atica.umufly.vuelos.adaptors.api.rest.v2;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.AuthService;
import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v2.dto.VueloDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliado;
import es.um.atica.umufly.vuelos.application.usecase.vuelos.GestionarVuelosUseCase;

@RestController( "v2.vuelosEndpoint" )
public class VuelosEndpoint {

	private final GestionarVuelosUseCase getVuelosAmpliadosUseCase;
	private final VuelosModelAssembler vuelosModelAssembler;
	private final PagedResourcesAssembler<VueloAmpliado> pagedResourcesAssembler;
	private final AuthService authService;

	public VuelosEndpoint( GestionarVuelosUseCase getVuelosAmpliadosUseCase, VuelosModelAssembler vuelosModelAssembler, PagedResourcesAssembler<VueloAmpliado> pagedResourcesAssembler, AuthService authService ) {
		this.getVuelosAmpliadosUseCase = getVuelosAmpliadosUseCase;
		this.vuelosModelAssembler = vuelosModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_VUELOS )
	public CollectionModel<VueloDTO> getVuelos( @RequestHeader( name = "UMU-Usuario", required = true ) String usuario, @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size ) {
		return pagedResourcesAssembler.toModel( getVuelosAmpliadosUseCase.getVuelos( authService.parseUserHeader( usuario ), page, size ), vuelosModelAssembler );
	}

}
