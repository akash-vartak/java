package de.benjaminborbe.loggly;

import com.google.inject.Provider;
import de.benjaminborbe.loggly.api.LogglyService;
import de.benjaminborbe.loggly.guice.LogglyModules;
import de.benjaminborbe.loggly.util.LogglyConnector;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.BaseBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LogglyActivator extends BaseBundleActivator {

	@Inject
	private LogglyService logglyService;

	@Inject
	private Provider<LogglyConnector> logglyConnectorProvider;

	@Override
	protected Modules getModules(final BundleContext context) {
		return new LogglyModules(context);
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<>(super.getServiceInfos());
		result.add(new ServiceInfo(LogglyService.class, logglyService));
		return result;
	}

	@Override
	public Collection<ServiceTracker> getServiceTrackers(final BundleContext context) {
		final Set<ServiceTracker> serviceTrackers = new HashSet<>(super.getServiceTrackers(context));
		// serviceTrackers.add(new LogglyServiceTracker(logglyRegistry, context,
		// LogglyService.class));
		return serviceTrackers;
	}

	@Override
	protected void onStopped() {
		super.onStopped();
		try {
			logglyConnectorProvider.get().close();
		} catch (final Exception e) {
			// nop
		}
	}

}
