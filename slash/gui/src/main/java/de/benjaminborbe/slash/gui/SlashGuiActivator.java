package de.benjaminborbe.slash.gui;

import de.benjaminborbe.slash.gui.guice.SlashGuiModules;
import de.benjaminborbe.slash.gui.servlet.SlashGuiLogFilter;
import de.benjaminborbe.slash.gui.servlet.SlashGuiRobotsTxtServlet;
import de.benjaminborbe.slash.gui.servlet.SlashGuiServlet;
import de.benjaminborbe.slash.gui.servlet.SlashGuiSessionTestServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.FilterInfo;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ResourceInfo;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import de.benjaminborbe.tools.osgi.ServletInfo;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SlashGuiActivator extends HttpBundleActivator {

	@Inject
	private SlashGuiServlet slashServlet;

	@Inject
	private SlashGuiLogFilter slashLogFilter;

	@Inject
	private SlashGuiRobotsTxtServlet slashRobotsTxtServlet;

	@Inject
	private SlashGuiSessionTestServlet slashGuiSessionTestServlet;

	@Inject
	private ExtHttpServiceMock extHttpServiceMock;

	public SlashGuiActivator() {
		super(SlashGuiConstants.NAME);
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new SlashGuiModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>(super.getServletInfos());
		result.add(new ServletInfo(slashServlet, "/", true));
		result.add(new ServletInfo(slashRobotsTxtServlet, "/robots.txt", true));
		result.add(new ServletInfo(slashGuiSessionTestServlet, "/sessionTest", true));
		return result;
	}

	@Override
	protected Collection<FilterInfo> getFilterInfos() {
		final Set<FilterInfo> result = new HashSet<FilterInfo>(super.getFilterInfos());
		result.add(new FilterInfo(slashLogFilter, ".*", 1, true));
		return result;
	}

	@Override
	protected Collection<ResourceInfo> getResouceInfos() {
		final Set<ResourceInfo> result = new HashSet<ResourceInfo>(super.getResouceInfos());
		result.add(new ResourceInfo("/css", "css", true));
		result.add(new ResourceInfo("/js", "js", true));
		result.add(new ResourceInfo("/images", "images", true));
		return result;
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<ServiceInfo>(super.getServiceInfos());
		result.add(new ServiceInfo(ExtHttpService.class, extHttpServiceMock));
		return result;
	}
}
