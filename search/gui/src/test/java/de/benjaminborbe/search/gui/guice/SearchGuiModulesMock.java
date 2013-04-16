package de.benjaminborbe.search.gui.guice;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Module;

import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.guice.ToolModuleMock;
import de.benjaminborbe.tools.osgi.mock.PeaberryModuleMock;
import de.benjaminborbe.tools.osgi.mock.ServletModuleMock;
import de.benjaminborbe.website.guice.WebsiteOsgiModuleMock;

public class SearchGuiModulesMock implements Modules {

	@Override
	public Collection<Module> getModules() {
		return Arrays
				.asList(new PeaberryModuleMock(), new ServletModuleMock(), new SearchGuiOsgiModuleMock(), new SearchGuiModule(), new ToolModuleMock(), new WebsiteOsgiModuleMock());
	}
}