package de.benjaminborbe.performance.gui.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import de.benjaminborbe.analytics.api.AnalyticsService;
import de.benjaminborbe.analytics.mock.AnalyticsServiceMock;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.mock.AuthenticationServiceMock;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.mock.AuthorizationServiceMock;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.navigation.mock.NavigationWidgetMock;
import de.benjaminborbe.performance.api.PerformanceService;
import de.benjaminborbe.performance.gui.mock.PerformanceServiceMock;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;
import de.benjaminborbe.tools.osgi.mock.LogServiceMock;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

public class PerformanceGuiOsgiModuleMock extends AbstractModule {

	@Override
	protected void configure() {

		bind(AnalyticsService.class).to(AnalyticsServiceMock.class).in(Singleton.class);
		bind(AuthorizationService.class).to(AuthorizationServiceMock.class).in(Singleton.class);
		bind(AuthenticationService.class).to(AuthenticationServiceMock.class).in(Singleton.class);
		bind(PerformanceService.class).to(PerformanceServiceMock.class).in(Singleton.class);
		bind(NavigationWidget.class).to(NavigationWidgetMock.class).in(Singleton.class);
		bind(LogService.class).to(LogServiceMock.class).in(Singleton.class);
		bind(ExtHttpService.class).to(ExtHttpServiceMock.class).in(Singleton.class);
	}
}