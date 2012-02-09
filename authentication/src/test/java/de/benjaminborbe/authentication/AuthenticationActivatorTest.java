package de.benjaminborbe.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;

import de.benjaminborbe.authentication.guice.AuthenticationModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;
import de.benjaminborbe.tools.osgi.test.BundleActivatorTestUtil;

public class AuthenticationActivatorTest {

	@Test
	public void testInject() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new AuthenticationModulesMock());
		final AuthenticationActivator o = injector.getInstance(AuthenticationActivator.class);
		assertNotNull(o);
	}

	@Test
	public void testResources() throws Exception {
		final Injector injector = GuiceInjectorBuilder.getInjector(new AuthenticationModulesMock());
		final AuthenticationActivator o = new AuthenticationActivator() {

			@Override
			public Injector getInjector() {
				return injector;
			}

		};
		final BundleActivatorTestUtil bundleActivatorTestUtil = new BundleActivatorTestUtil();
		final ExtHttpServiceMock extHttpServiceMock = bundleActivatorTestUtil.startBundle(o);
		final List<String> paths = Arrays.asList();
		assertEquals(paths.size(), extHttpServiceMock.getRegisterResourceCallCounter());
		for (final String path : paths) {
			assertTrue("no resource for path " + path + " registered", extHttpServiceMock.hasResource(path));
		}
	}

	@Test
	public void testServices() {
		final AuthenticationActivator authenticationActivator = new AuthenticationActivator();
		final Collection<ServiceInfo> serviceInfos = authenticationActivator.getServiceInfos();
		assertEquals(1, serviceInfos.size());
	}

	@Test
	public void testGetModules() {
		final AuthenticationActivator authenticationActivator = new AuthenticationActivator();
		final BundleContext context = EasyMock.createMock(BundleContext.class);
		EasyMock.replay(context);
		assertNotNull(authenticationActivator.getModules(context));
	}
}
