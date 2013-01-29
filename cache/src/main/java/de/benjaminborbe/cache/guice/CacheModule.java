package de.benjaminborbe.cache.guice;

import org.slf4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.cache.service.CacheServiceImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;

public class CacheModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CacheService.class).to(CacheServiceImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}
