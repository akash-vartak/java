package de.benjaminborbe.scala.gui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import de.benjaminborbe.scala.gui.guice.ScalaGuiModules;
import de.benjaminborbe.scala.gui.servlet.ScalaGuiServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ServletInfo;

public class ScalaGuiActivator extends HttpBundleActivator {

	@Inject
	private ScalaGuiServlet scalaGuiServlet;

	public ScalaGuiActivator() {
		super(ScalaGuiConstants.NAME);
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new ScalaGuiModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>(super.getServletInfos());
		result.add(new ServletInfo(scalaGuiServlet, ScalaGuiConstants.URL_HOME));
		return result;
	}

}
