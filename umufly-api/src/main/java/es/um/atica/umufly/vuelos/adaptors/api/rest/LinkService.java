package es.um.atica.umufly.vuelos.adaptors.api.rest;

import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

@Component
public class LinkService {

	private UriBuilder base() {
		return new DefaultUriBuilderFactory( BasicLinkBuilder.linkToCurrentMapping().toString() ).builder();
	}

	public UriBuilder privateApiV2() {
		return base().path( Constants.PRIVATE_PREFIX ).path( Constants.API_VERSION_2 );
	}

}
