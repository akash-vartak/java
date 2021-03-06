package de.benjaminborbe.navigation.util;

import de.benjaminborbe.navigation.api.NavigationEntry;
import org.easymock.EasyMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NavigationEntryRegistryUnitTest {

	@Test
	public void testSimple() {
		final NavigationEntry e = EasyMock.createMock(NavigationEntry.class);
		EasyMock.replay(e);

		final NavigationEntryRegistry r = new NavigationEntryRegistry();
		assertNotNull(r);
		assertEquals(0, r.getAll().size());
		r.add(e);
		assertEquals(1, r.getAll().size());
	}
}
