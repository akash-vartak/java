package de.benjaminborbe.vnc.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VncLocationImplTest {

	@Test
	public void testEquals() throws Exception {
		assertTrue(new VncLocationImpl(1, 1).equals(new VncLocationImpl(1, 1)));
		assertFalse(new VncLocationImpl(1, 1).equals(new VncLocationImpl(2, 1)));
		assertFalse(new VncLocationImpl(1, 1).equals(new VncLocationImpl(2, 1)));
		assertFalse(new VncLocationImpl(1, 1).equals(null));
	}
}
