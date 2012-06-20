package de.benjaminborbe.lunch.gui.servlet;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.google.inject.Injector;
import de.benjaminborbe.lunch.gui.guice.LunchGuiModulesMock;
import de.benjaminborbe.lunch.gui.servlet.LunchGuiServlet;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;

public class LunchGuiServletIntegrationTest {

	@Test
	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new LunchGuiModulesMock());
		final LunchGuiServlet a = injector.getInstance(LunchGuiServlet.class);
		final LunchGuiServlet b = injector.getInstance(LunchGuiServlet.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}
}
