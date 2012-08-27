package de.benjaminborbe.geocaching.gui.guice;

import static org.ops4j.peaberry.Peaberry.service;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.geocaching.api.GeocachingService;

public class GeocachingGuiOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthenticationService.class).toProvider(service(AuthenticationService.class).single());
		bind(GeocachingService.class).toProvider(service(GeocachingService.class).single());
		bind(NavigationWidget.class).toProvider(service(NavigationWidget.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
