package de.benjaminborbe.timetracker.guice;

import java.util.Arrays;
import java.util.Collection;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleContext;

import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;

import de.benjaminborbe.tools.guice.Modules;

public class TimetrackerModules implements Modules {

	private final BundleContext context;

	public TimetrackerModules(final BundleContext context) {
		this.context = context;
	}

	@Override
	public Collection<Module> getModules() {
		return Arrays.asList(Peaberry.osgiModule(context), new ServletModule(), new TimetrackerOsgiModule(),
				new TimetrackerModule());
	}

}