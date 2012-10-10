package de.benjaminborbe.monitoring.guice;

import static org.ops4j.peaberry.Peaberry.service;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.mail.api.MailService;

public class MonitoringOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthorizationService.class).toProvider(service(AuthorizationService.class).single());
		bind(ConfigurationService.class).toProvider(service(ConfigurationService.class).single());
		bind(MailService.class).toProvider(service(MailService.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
