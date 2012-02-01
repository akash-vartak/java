package de.benjaminborbe.monitoring.gui.guice;

import static org.ops4j.peaberry.Peaberry.service;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.benjaminborbe.monitoring.api.MonitoringService;
import de.benjaminborbe.navigation.api.NavigationWidget;

public class MonitoringGuiOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MonitoringService.class).toProvider(service(MonitoringService.class).single());
		bind(NavigationWidget.class).toProvider(service(NavigationWidget.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
