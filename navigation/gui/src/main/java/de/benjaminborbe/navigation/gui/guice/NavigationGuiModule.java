package de.benjaminborbe.navigation.gui.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.navigation.gui.service.NavigationGuiWidgetImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;
import org.slf4j.Logger;

import javax.inject.Singleton;

public class NavigationGuiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NavigationWidget.class).to(NavigationGuiWidgetImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}
