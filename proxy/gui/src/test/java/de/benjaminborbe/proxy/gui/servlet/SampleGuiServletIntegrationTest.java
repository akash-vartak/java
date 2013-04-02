package de.benjaminborbe.proxy.gui.servlet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.Injector;

import de.benjaminborbe.proxy.gui.guice.ProxyGuiModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;

public class ProxyGuiServletIntegrationTest {

	@Test
	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new ProxyGuiModulesMock());
		final ProxyGuiServlet a = injector.getInstance(ProxyGuiServlet.class);
		final ProxyGuiServlet b = injector.getInstance(ProxyGuiServlet.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}

}
