package de.benjaminborbe.httpdownloader.tools;

import de.benjaminborbe.httpdownloader.api.HttpHeader;
import de.benjaminborbe.httpdownloader.api.HttpResponse;
import org.easymock.EasyMock;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class HttpUtilUnitTest {

	@Test
	public void testIsAvailable() {
		final HttpUtil httpUtil = new HttpUtil();
		{
			final HttpResponse httpResponse = EasyMock.createMock(HttpResponse.class);
			EasyMock.expect(httpResponse.getReturnCode()).andReturn(null);
			EasyMock.replay(httpResponse);
			assertThat(httpUtil.isAvailable(httpResponse), CoreMatchers.is(false));
		}
		{
			final HttpResponse httpResponse = EasyMock.createMock(HttpResponse.class);
			EasyMock.expect(httpResponse.getReturnCode()).andReturn(404);
			EasyMock.replay(httpResponse);
			assertThat(httpUtil.isAvailable(httpResponse), CoreMatchers.is(false));
		}
		{
			final HttpResponse httpResponse = EasyMock.createMock(HttpResponse.class);
			EasyMock.expect(httpResponse.getReturnCode()).andReturn(200);
			EasyMock.replay(httpResponse);
			assertThat(httpUtil.isAvailable(httpResponse), CoreMatchers.is(true));
		}
	}

	@Test
	public void testIsHtml() throws Exception {
		final HttpUtil httpUtil = new HttpUtil();
		{
			final HttpHeader httpHeader = EasyMock.createMock(HttpHeader.class);
			EasyMock.expect(httpHeader.getValue("Content-Type")).andReturn(null);
			EasyMock.replay(httpHeader);
			assertThat(httpUtil.isHtml(httpHeader), CoreMatchers.is(false));
		}
		{
			final HttpHeader httpHeader = EasyMock.createMock(HttpHeader.class);
			EasyMock.expect(httpHeader.getValue("Content-Type")).andReturn("text/plain");
			EasyMock.replay(httpHeader);
			assertThat(httpUtil.isHtml(httpHeader), CoreMatchers.is(false));
		}
		{
			final HttpHeader httpHeader = EasyMock.createMock(HttpHeader.class);
			EasyMock.expect(httpHeader.getValue("Content-Type")).andReturn("text/html");
			EasyMock.replay(httpHeader);
			assertThat(httpUtil.isHtml(httpHeader), CoreMatchers.is(true));
		}
		{
			final HttpHeader httpHeader = EasyMock.createMock(HttpHeader.class);
			EasyMock.expect(httpHeader.getValue("Content-Type")).andReturn("text/html;charset=UTF-8");
			EasyMock.replay(httpHeader);
			assertThat(httpUtil.isHtml(httpHeader), CoreMatchers.is(true));
		}

	}
}