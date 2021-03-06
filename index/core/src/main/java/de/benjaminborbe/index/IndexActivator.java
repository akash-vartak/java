package de.benjaminborbe.index;

import de.benjaminborbe.configuration.api.ConfigurationDescription;
import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.index.config.IndexConfig;
import de.benjaminborbe.index.guice.IndexModules;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.BaseBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class IndexActivator extends BaseBundleActivator {

	@Inject
	private IndexService indexerService;

	@Inject
	private IndexConfig indexConfig;

	@Override
	protected Modules getModules(final BundleContext context) {
		return new IndexModules(context);
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<ServiceInfo>(super.getServiceInfos());
		result.add(new ServiceInfo(IndexService.class, indexerService));
		for (final ConfigurationDescription configuration : indexConfig.getConfigurations()) {
			result.add(new ServiceInfo(ConfigurationDescription.class, configuration, configuration.getName()));
		}
		return result;
	}

}
