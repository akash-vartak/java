package de.benjaminborbe.streamcache.test;

import junit.framework.Test;
import org.apache.felix.ipojo.junit4osgi.OSGiTestSuite;
import org.osgi.framework.BundleContext;

public class StreamcacheTestSuite {

	public static Test suite(final BundleContext bc) {
		final OSGiTestSuite ots = new OSGiTestSuite("Streamcache Test Suite", bc);
		ots.addTestSuite(StreamcacheIntegrationTest.class);
		return ots;
	}
}
