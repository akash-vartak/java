package de.benjaminborbe.bookmark;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import de.benjaminborbe.bookmark.api.BookmarkService;
import de.benjaminborbe.bookmark.guice.BookmarkModules;
import de.benjaminborbe.bookmark.service.BookmarkSearchService;
import de.benjaminborbe.bookmark.servlet.BookmarkServlet;
import de.benjaminborbe.search.api.SearchServiceComponent;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;

public class BookmarkActivator implements BundleActivator {

	private final Set<ServiceRegistration> serviceRegistrations = new HashSet<ServiceRegistration>();

	private Injector injector;

	private ServiceTracker extHttpServiceTracker;

	@Inject
	private Logger logger;

	@Inject
	private GuiceFilter guiceFilter;

	@Inject
	private BookmarkServlet bookmarkServlet;

	@Inject
	private BookmarkService bookmarkService;

	@Inject
	private BookmarkSearchService bookmarkSearchService;

	@Override
	public void start(final BundleContext context) throws Exception {
		try {
			getInjector(context);
			injector.injectMembers(this);

			// create serviceTracker for ExtHttpService
			extHttpServiceTracker = new ServiceTracker(context, ExtHttpService.class.getName(), null) {

				@Override
				public Object addingService(final ServiceReference ref) {
					final Object service = super.addingService(ref);
					serviceAdded((ExtHttpService) service);
					return service;
				}

				@Override
				public void removedService(final ServiceReference ref, final Object service) {
					serviceRemoved((ExtHttpService) service);
					super.removedService(ref, service);
				}
			};
			extHttpServiceTracker.open();

			// register BookmarkService
			{
				final Properties props = new Properties();
				serviceRegistrations.add(context.registerService(BookmarkService.class.getName(), bookmarkService, props));
			}

			// register BookmarkSearchService
			{
				final Properties props = new Properties();
				props.put("name", bookmarkSearchService.getClass().getName());
				serviceRegistrations.add(context.registerService(SearchServiceComponent.class.getName(), bookmarkSearchService,
						props));
			}

		}
		catch (final Exception e) {
			if (logger != null) {
				logger.error("starting: " + this.getClass().getName() + " failed: " + e.toString(), e);
			}
			// fallback if injector start fails!
			else {
				e.printStackTrace();
				System.out.println("starting: " + this.getClass().getName() + " failed: " + e.toString());
			}
			throw e;
		}

	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		try {
			final Injector injector = getInjector(context);
			injector.injectMembers(this);
			logger.info("stopping: " + this.getClass().getName() + " ...");

			// close tracker
			extHttpServiceTracker.close();

			// unregister all services
			for (final ServiceRegistration serviceRegistration : serviceRegistrations) {
				serviceRegistration.unregister();
			}
			serviceRegistrations.clear();

			logger.info("stopping: " + this.getClass().getName() + " done");
		}
		catch (final Exception e) {
			if (logger != null) {
				logger.error("stopping: " + this.getClass().getName() + " failed: " + e.toString(), e);
			}
			// fallback if injector start fails!
			else {
				e.printStackTrace();
				System.out.println("stopping: " + this.getClass().getName() + " failed: " + e.toString());
			}
			throw e;
		}
	}

	private Injector getInjector(final BundleContext context) {
		if (injector == null)
			injector = GuiceInjectorBuilder.getInjector(new BookmarkModules(context));
		return injector;
	}

	private void serviceAdded(final ExtHttpService service) {
		logger.debug("Activator.serviceAdded(ExtHttpService)");
		try {

			// filter
			service.registerFilter(guiceFilter, ".*", null, 999, null);

			// servlet
			service.registerServlet("/bookmark", bookmarkServlet, null, null);
		}
		catch (final Exception e) {
			logger.error("error during service activation", e);
		}
	}

	private void serviceRemoved(final ExtHttpService service) {
		logger.debug("Activator.serviceRemoved(ExtHttpService)");

		// filter
		service.unregisterFilter(guiceFilter);

		// servlet
		service.unregisterServlet(bookmarkServlet);
	}

}