package de.benjaminborbe.proxy.core.guice;

import de.benjaminborbe.proxy.core.service.ProxyCoreServiceImpl;
import org.slf4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.benjaminborbe.proxy.api.ProxyService;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;

public class ProxyCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ProxyService.class).to(ProxyCoreServiceImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}
