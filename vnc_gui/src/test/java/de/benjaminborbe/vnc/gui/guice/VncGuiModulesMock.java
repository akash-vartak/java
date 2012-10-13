package de.benjaminborbe.vnc.gui.guice;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Module;

import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.guice.ToolModule;
import de.benjaminborbe.tools.osgi.mock.PeaberryModuleMock;
import de.benjaminborbe.tools.osgi.mock.ServletModuleMock;
import de.benjaminborbe.vnc.gui.guice.VncGuiModule;
import de.benjaminborbe.website.guice.WebsiteModule;

public class VncGuiModulesMock implements Modules {

	@Override
	public Collection<Module> getModules() {
		return Arrays.asList(new PeaberryModuleMock(), new ServletModuleMock(), new VncGuiOsgiModuleMock(), new VncGuiModule(), new ToolModule(), new WebsiteModule());
	}
}
