package de.benjaminborbe.blog.gui.widget;

import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.tools.url.UrlUtil;
import org.easymock.EasyMock;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class BlogGuiFeedHeaderWidgetUnitTest {

	@Test
	public void testRender() throws Exception {
		final HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getContextPath()).andReturn("/myContext");
		EasyMock.expect(request.getScheme()).andReturn("http");
		EasyMock.expect(request.getServerName()).andReturn("www.example.com");
		EasyMock.replay(request);

		final StringWriter sw = new StringWriter();
		final PrintWriter writer = new PrintWriter(sw);

		final HttpServletResponse response = EasyMock.createMock(HttpServletResponse.class);
		EasyMock.expect(response.getWriter()).andReturn(writer).anyTimes();
		EasyMock.replay(response);

		final HttpContext context = EasyMock.createMock(HttpContext.class);
		EasyMock.replay(context);

		final UrlUtil urlUtil = EasyMock.createMock(UrlUtil.class);
		EasyMock.expect(urlUtil.buildBaseUrl(request)).andReturn("http://www.example.com/myContext");
		EasyMock.replay(urlUtil);

		final BlogGuiFeedHeaderWidget widget = new BlogGuiFeedHeaderWidget(urlUtil);
		widget.render(request, response, context);
		assertEquals("<link href=\"http://www.example.com/myContext/blog/atom.xml\" title=\"bb News\" type=\"application/atom+xml\"/>", sw.toString());
	}
}
