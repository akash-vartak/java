package de.benjaminborbe.authorization;

import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.guice.AuthorizationModules;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.BaseBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthorizationActivator extends BaseBundleActivator {

	@Inject
	private AuthorizationService authorizationService;

	@Override
	protected Modules getModules(final BundleContext context) {
		return new AuthorizationModules(context);
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<ServiceInfo>(super.getServiceInfos());
		result.add(new ServiceInfo(AuthorizationService.class, authorizationService));
		return result;
	}

	@Override
	public Collection<ServiceTracker> getServiceTrackers(final BundleContext context) {
		final Set<ServiceTracker> serviceTrackers = new HashSet<ServiceTracker>(super.getServiceTrackers(context));
		// serviceTrackers.add(new AuthorizationServiceTracker(authorizationRegistry, context,
		// AuthorizationService.class));
		return serviceTrackers;
	}
}
