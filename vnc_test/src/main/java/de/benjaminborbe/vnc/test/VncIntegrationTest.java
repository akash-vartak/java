package de.benjaminborbe.vnc.test;

import org.apache.felix.http.api.ExtHttpService;
import org.apache.felix.ipojo.junit4osgi.OSGiTestCase;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import de.benjaminborbe.vnc.api.VncService;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;

public class VncIntegrationTest extends OSGiTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetExtHttpService() {

		final BundleContext bundleContext = getContext();
		assertNotNull(bundleContext);
		final ExtHttpServiceMock extHttpService = new ExtHttpServiceMock();
		assertNotNull(extHttpService);
		// zum start: keine Dienste registriert
		assertEquals(0, extHttpService.getRegisterFilterCallCounter());
		assertEquals(0, extHttpService.getRegisterServletCallCounter());
		assertEquals(0, extHttpService.getUnregisterFilterCallCounter());
		assertEquals(0, extHttpService.getUnregisterServletCallCounter());
		final ServiceRegistration serviceRegistration = bundleContext.registerService(ExtHttpService.class.getName(), extHttpService, null);
		assertNotNull(serviceRegistration);
		// nach start: Dienste vorhanden?
		assertTrue("no filters registered", extHttpService.getRegisterFilterCallCounter() > 0);
		assertTrue("no servlets registered.", extHttpService.getRegisterServletCallCounter() > 0);
		assertEquals(0, extHttpService.getUnregisterFilterCallCounter());
		assertEquals(0, extHttpService.getUnregisterServletCallCounter());

		// do unregister
		serviceRegistration.unregister();

		assertTrue("no servlets unregistered", extHttpService.getUnregisterServletCallCounter() > 0);
		assertEquals(extHttpService.getRegisterServletCallCounter(), extHttpService.getRegisterServletCallCounter());
		assertEquals(extHttpService.getRegisterFilterCallCounter(), extHttpService.getUnregisterFilterCallCounter());
	}

	// @Test public void testServices() throws Exception {
	// final BundleContext bundleContext = getContext();
	// assertNotNull(bundleContext);
	// for (final ServiceReference a : bundleContext.getAllServiceReferences(null, null)) {
	// // final Bundle bundle = a.getBundle();
	// final Object service = bundleContext.getService(a);
	// System.err.println(service);
	// }
	// }

	@Test
	public void testVncService() {
		final Object serviceObject = getServiceObject(VncService.class.getName(), null);
		final VncService service = (VncService) serviceObject;
		assertNotNull(service);
		assertEquals("de.benjaminborbe.vnc.service.VncServiceImpl", service.getClass().getName());
	}

}
