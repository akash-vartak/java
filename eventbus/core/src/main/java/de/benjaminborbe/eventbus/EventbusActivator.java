package de.benjaminborbe.eventbus;

import de.benjaminborbe.eventbus.api.EventbusService;
import de.benjaminborbe.eventbus.guice.EventbusModules;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.BaseBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EventbusActivator extends BaseBundleActivator {

	@Inject
	private EventbusService EventbusService;

	@Override
	protected Modules getModules(final BundleContext context) {
		return new EventbusModules(context);
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<ServiceInfo>(super.getServiceInfos());
		result.add(new ServiceInfo(EventbusService.class, EventbusService));
		return result;
	}
}
