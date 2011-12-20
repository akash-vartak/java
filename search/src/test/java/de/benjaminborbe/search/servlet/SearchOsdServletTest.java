package de.benjaminborbe.search.servlet;

import junit.framework.TestCase;

import com.google.inject.Injector;

import de.benjaminborbe.search.guice.SearchModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;

public class SearchOsdServletTest extends TestCase {

	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new SearchModulesMock());
		final SearchOsdServlet a = injector.getInstance(SearchOsdServlet.class);
		final SearchOsdServlet b = injector.getInstance(SearchOsdServlet.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}
}
