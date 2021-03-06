package de.benjaminborbe.index.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.distributed.search.api.DistributedSearchService;
import de.benjaminborbe.lucene.index.api.LuceneIndexService;
import de.benjaminborbe.navigation.api.NavigationWidget;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import static org.ops4j.peaberry.Peaberry.service;

public class IndexOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DistributedSearchService.class).toProvider(service(DistributedSearchService.class).single());
		bind(LuceneIndexService.class).toProvider(service(LuceneIndexService.class).single());
		bind(ConfigurationService.class).toProvider(service(ConfigurationService.class).single());
		bind(NavigationWidget.class).toProvider(service(NavigationWidget.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
