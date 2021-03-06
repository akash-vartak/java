package de.benjaminborbe.confluence.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.analytics.api.AnalyticsService;
import de.benjaminborbe.analytics.mock.AnalyticsServiceMock;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.mock.AuthenticationServiceMock;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.mock.AuthorizationServiceMock;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.configuration.mock.ConfigurationServiceMock;
import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.index.mock.IndexServiceMock;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.memory.service.StorageServiceMemory;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;
import de.benjaminborbe.tools.osgi.mock.LogServiceMock;
import de.benjaminborbe.xmlrpc.api.XmlrpcService;
import de.benjaminborbe.xmlrpc.mock.XmlrpcServiceMock;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import javax.inject.Singleton;

public class ConfluenceOsgiModuleMock extends AbstractModule {

	@Override
	protected void configure() {
		bind(XmlrpcService.class).to(XmlrpcServiceMock.class).in(Singleton.class);
		bind(AnalyticsService.class).to(AnalyticsServiceMock.class).in(Singleton.class);
		bind(ConfigurationService.class).to(ConfigurationServiceMock.class).in(Singleton.class);
		bind(IndexService.class).to(IndexServiceMock.class).in(Singleton.class);
		bind(AuthenticationService.class).to(AuthenticationServiceMock.class).in(Singleton.class);
		bind(AuthorizationService.class).to(AuthorizationServiceMock.class).in(Singleton.class);
		bind(StorageService.class).to(StorageServiceMemory.class).in(Singleton.class);
		bind(LogService.class).to(LogServiceMock.class).in(Singleton.class);
		bind(ExtHttpService.class).to(ExtHttpServiceMock.class).in(Singleton.class);
	}
}
