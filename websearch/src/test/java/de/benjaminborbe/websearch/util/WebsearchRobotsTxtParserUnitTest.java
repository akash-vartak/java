package de.benjaminborbe.websearch.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WebsearchRobotsTxtParserUnitTest {

	@Test
	public void testParse() throws Exception {
		final WebsearchRobotsTxtParser parser = new WebsearchRobotsTxtParser();
		assertNotNull(parser.parseRobotsTxt(null));
		assertNotNull(parser.parseRobotsTxt(""));

		{
			final StringBuilder sb = new StringBuilder();
			sb.append("User-agent: Foo-Bot\n");
			sb.append("Disallow: /\n");

			final WebsearchRobotsTxt txt = parser.parseRobotsTxt(sb.toString());
			assertThat(txt.isAllowed("Foo-Bot", "/"), is(false));
			assertThat(txt.isAllowed("Foo-Bot", "/bar"), is(false));
		}

		{
			final StringBuilder sb = new StringBuilder();
			sb.append("User-agent: *\n");
			sb.append("Disallow: /\n");

			final WebsearchRobotsTxt txt = parser.parseRobotsTxt(sb.toString());
			assertThat(txt.isAllowed("Foo-Bot", "/"), is(false));
			assertThat(txt.isAllowed("Foo-Bot", "/bar"), is(false));
		}

		{
			final StringBuilder sb = new StringBuilder();
			sb.append("User-agent: Foo-Bot\n");
			sb.append("Disallow: /\n");
			sb.append("User-agent: *\n");
			sb.append("Disallow: /cgi\n");

			final WebsearchRobotsTxt txt = parser.parseRobotsTxt(sb.toString());
			assertThat(txt.isAllowed("My-Bot", "/"), is(true));
			assertThat(txt.isAllowed("My-Bot", "/bar"), is(true));
			assertThat(txt.isAllowed("My-Bot", "/cgi"), is(false));

			assertThat(txt.isAllowed("Foo-Bot", "/"), is(false));
			assertThat(txt.isAllowed("Foo-Bot", "/bar"), is(false));
			assertThat(txt.isAllowed("Foo-Bot", "/cgi"), is(false));
		}
	}
}
