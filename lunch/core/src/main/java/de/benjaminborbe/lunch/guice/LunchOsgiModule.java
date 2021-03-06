package de.benjaminborbe.lunch.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.kiosk.api.KioskService;
import de.benjaminborbe.notification.api.NotificationService;
import de.benjaminborbe.storage.api.StorageService;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import static org.ops4j.peaberry.Peaberry.service;

public class LunchOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NotificationService.class).toProvider(service(NotificationService.class).single());
		bind(StorageService.class).toProvider(service(StorageService.class).single());
		bind(KioskService.class).toProvider(service(KioskService.class).single());
		bind(AuthenticationService.class).toProvider(service(AuthenticationService.class).single());
		bind(AuthorizationService.class).toProvider(service(AuthorizationService.class).single());
		bind(ConfigurationService.class).toProvider(service(ConfigurationService.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
