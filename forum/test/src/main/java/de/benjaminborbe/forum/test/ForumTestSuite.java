package de.benjaminborbe.forum.test;

import junit.framework.Test;
import org.apache.felix.ipojo.junit4osgi.OSGiTestSuite;
import org.osgi.framework.BundleContext;

public class ForumTestSuite {

	public static Test suite(final BundleContext bc) {
		final OSGiTestSuite ots = new OSGiTestSuite("Forum Test Suite", bc);
		ots.addTestSuite(ForumIntegrationTest.class);
		return ots;
	}
}
