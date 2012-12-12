package de.benjaminborbe.forum.gui.servlet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.Injector;

import de.benjaminborbe.forum.gui.guice.ForumGuiModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;

public class ForumGuiServletIntegrationTest {

	@Test
	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new ForumGuiModulesMock());
		final ForumGuiServlet a = injector.getInstance(ForumGuiServlet.class);
		final ForumGuiServlet b = injector.getInstance(ForumGuiServlet.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}

}
