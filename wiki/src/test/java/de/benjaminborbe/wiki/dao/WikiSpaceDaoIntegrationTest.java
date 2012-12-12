package de.benjaminborbe.wiki.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.inject.Injector;

import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import de.benjaminborbe.wiki.guice.WikiModulesMock;

public class WikiSpaceDaoIntegrationTest {

	@Test
	public void testInject() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new WikiModulesMock());
		assertNotNull(injector.getInstance(WikiSpaceDao.class));
		assertEquals(WikiSpaceDaoImpl.class, injector.getInstance(WikiSpaceDao.class).getClass());
	}

}
