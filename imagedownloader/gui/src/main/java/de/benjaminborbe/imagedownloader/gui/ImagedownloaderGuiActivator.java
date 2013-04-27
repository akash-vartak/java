package de.benjaminborbe.imagedownloader.gui;

import de.benjaminborbe.imagedownloader.gui.guice.ImagedownloaderGuiModules;
import de.benjaminborbe.imagedownloader.gui.servlet.ImagedownloaderGuiServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ServletInfo;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ImagedownloaderGuiActivator extends HttpBundleActivator {

	@Inject
	private ImagedownloaderGuiServlet imagedownloaderGuiServlet;

	public ImagedownloaderGuiActivator() {
		super(ImagedownloaderGuiConstants.NAME);
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new ImagedownloaderGuiModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<>(super.getServletInfos());
		result.add(new ServletInfo(imagedownloaderGuiServlet, ImagedownloaderGuiConstants.URL_HOME));
		return result;
	}

}
