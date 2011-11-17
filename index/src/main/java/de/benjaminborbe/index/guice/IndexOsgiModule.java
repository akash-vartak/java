package de.benjaminborbe.index.guice;

import static org.ops4j.peaberry.Peaberry.service;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

public class IndexOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
