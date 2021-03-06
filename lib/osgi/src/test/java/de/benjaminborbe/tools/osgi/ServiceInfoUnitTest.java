package de.benjaminborbe.tools.osgi;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ServiceInfoUnitTest {

	@Test
	public void testnewByClassObjectString() {
		final Class<TestService> clazz = TestService.class;
		final TestService service = new TestServiceImpl();
		final String name = "test";
		final ServiceInfo serviceInfo = new ServiceInfo(clazz, service, name);
		assertEquals(clazz.getName(), serviceInfo.getName());
		assertEquals(service, serviceInfo.getService());
		assertEquals(1, serviceInfo.getProperties().size());
		assertEquals(name, serviceInfo.getProperties().get("name"));
	}

	@Test
	public void testnewByClassObjectProperties() {
		final Class<?> clazz = TestService.class;
		final Object service = new TestServiceImpl();
		final Properties properties = new Properties();
		properties.put("foo", "bar");
		final ServiceInfo serviceInfo = new ServiceInfo(clazz, service, properties);
		assertEquals(clazz.getName(), serviceInfo.getName());
		assertEquals(service, serviceInfo.getService());
		assertEquals(1, serviceInfo.getProperties().size());
		assertEquals("bar", serviceInfo.getProperties().get("foo"));
	}

	@Test
	public void testnewByClassObject() {
		final Class<?> clazz = TestService.class;
		final Object service = new TestServiceImpl();
		final ServiceInfo serviceInfo = new ServiceInfo(clazz, service);
		assertEquals(clazz.getName(), serviceInfo.getName());
		assertEquals(service, serviceInfo.getService());
		assertEquals(0, serviceInfo.getProperties().size());
	}
}

interface TestService {

}

class TestServiceImpl implements TestService {

}
