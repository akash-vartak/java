package de.benjaminborbe.websearch.core.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.crawler.api.CrawlerService;
import de.benjaminborbe.httpdownloader.api.HttpdownloaderService;
import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.storage.api.StorageService;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import static org.ops4j.peaberry.Peaberry.service;

public class WebsearchOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(HttpdownloaderService.class).toProvider(service(HttpdownloaderService.class).single());
		bind(ConfigurationService.class).toProvider(service(ConfigurationService.class).single());
		bind(AuthenticationService.class).toProvider(service(AuthenticationService.class).single());
		bind(AuthorizationService.class).toProvider(service(AuthorizationService.class).single());
		bind(StorageService.class).toProvider(service(StorageService.class).single());
		bind(CrawlerService.class).toProvider(service(CrawlerService.class).single());
		bind(IndexService.class).toProvider(service(IndexService.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
