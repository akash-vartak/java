package de.benjaminborbe.tools.util;

import com.google.inject.Injector;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import de.benjaminborbe.tools.guice.ToolModules;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IdGeneratorIntegrationTest {

	@Test
	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new ToolModules());
		final IdGeneratorLong a = injector.getInstance(IdGeneratorLong.class);
		final IdGeneratorLong b = injector.getInstance(IdGeneratorLong.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}

}
