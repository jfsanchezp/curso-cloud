package es.um.atica.umufly.vuelos.adaptors.api.rest.v1;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.umufly.vuelos.adaptors.api.rest.Constants;
import es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto.VueloDTO;
import es.um.atica.umufly.vuelos.application.dto.VueloAmpliado;
import es.um.atica.umufly.vuelos.application.usecase.vuelos.GestionarVuelosUseCase;

@RestController( "v1.vuelosEndpoint" )
public class VuelosEndpoint {

	private final GestionarVuelosUseCase getVuelosUseCase;
	private final VuelosModelAssembler vuelosModelAssembler;
	private final PagedResourcesAssembler<VueloAmpliado> pagedResourcesAssembler;

	public VuelosEndpoint( GestionarVuelosUseCase getVuelosUseCase, VuelosModelAssembler vuelosModelAssembler, PagedResourcesAssembler<VueloAmpliado> pagedResourcesAssembler ) {
		this.getVuelosUseCase = getVuelosUseCase;
		this.vuelosModelAssembler = vuelosModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_1 + Constants.RESOURCE_VUELOS )
	public CollectionModel<VueloDTO> getVuelos( @RequestParam( name = "page", defaultValue = "0" ) int page, @RequestParam( name = "size", defaultValue = "25" ) int size ) {
		return pagedResourcesAssembler.toModel( getVuelosUseCase.getVuelos( null, page, size ), vuelosModelAssembler );
	}
}
