package de.benjaminborbe.virt.gui.guice;

import com.google.inject.Module;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.guice.ToolModuleMock;
import de.benjaminborbe.tools.osgi.mock.PeaberryModuleMock;
import de.benjaminborbe.tools.osgi.mock.ServletModuleMock;
import de.benjaminborbe.website.guice.WebsiteOsgiModuleMock;

import java.util.Arrays;
import java.util.Collection;

public class VirtGuiModulesMock implements Modules {

	@Override
	public Collection<Module> getModules() {
		return Arrays
			.asList(new PeaberryModuleMock(), new ServletModuleMock(), new VirtGuiOsgiModuleMock(), new VirtGuiModule(), new ToolModuleMock(), new WebsiteOsgiModuleMock());
	}
}
