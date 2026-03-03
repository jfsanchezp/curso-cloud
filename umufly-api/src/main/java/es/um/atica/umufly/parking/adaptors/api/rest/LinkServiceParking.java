package es.um.atica.umufly.parking.adaptors.api.rest;

import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

@Component
public class LinkServiceParking {

	private UriBuilder base() {
		return new DefaultUriBuilderFactory( BasicLinkBuilder.linkToCurrentMapping().toString() ).builder();
	}

	public UriBuilder privateApi() {
		return base().path( Constants.PRIVATE_PREFIX ).path( Constants.API_VERSION_1 );
	}

}
