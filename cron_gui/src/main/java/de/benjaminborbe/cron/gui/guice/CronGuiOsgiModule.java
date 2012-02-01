package de.benjaminborbe.cron.gui.guice;

import static org.ops4j.peaberry.Peaberry.service;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.benjaminborbe.cron.api.CronController;
import de.benjaminborbe.navigation.api.NavigationWidget;

public class CronGuiOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CronController.class).toProvider(service(CronController.class).single());
		bind(NavigationWidget.class).toProvider(service(NavigationWidget.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
