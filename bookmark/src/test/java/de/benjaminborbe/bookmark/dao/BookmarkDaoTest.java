package de.benjaminborbe.bookmark.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.Injector;

import de.benjaminborbe.bookmark.guice.BookmarkModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;

public class BookmarkDaoTest {

	@Test
	public void singleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new BookmarkModulesMock());
		final BookmarkDao a = injector.getInstance(BookmarkDao.class);
		final BookmarkDao b = injector.getInstance(BookmarkDao.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}
}
