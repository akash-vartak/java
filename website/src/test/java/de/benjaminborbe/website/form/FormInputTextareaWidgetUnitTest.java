package de.benjaminborbe.website.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Test;

import de.benjaminborbe.html.api.HttpContext;

public class FormInputTextareaWidgetUnitTest {

	@Test
	public void testGetName() {
		final String name = "test123";
		final FormInputTextareaWidget formInputTextareaWidget = new FormInputTextareaWidget(name);
		assertEquals(name, formInputTextareaWidget.getName());
	}

	@Test
	public void testRender() throws Exception, IOException {
		final String name = "test123";
		final FormInputTextareaWidget formInputTextareaWidget = new FormInputTextareaWidget(name);

		final HttpContext context = EasyMock.createMock(HttpContext.class);
		EasyMock.replay(context);

		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		final HttpServletResponse response = EasyMock.createMock(HttpServletResponse.class);
		EasyMock.expect(response.getWriter()).andReturn(printWriter);
		EasyMock.replay(response);

		final HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.replay(request);

		formInputTextareaWidget.render(request, response, context);

		assertEquals("<textarea name=\"" + name + "\"></textarea>", stringWriter.toString());
	}

	@Test
	public void testAddLabel() {
		final String name = "test123";
		final FormInputTextareaWidget formInputTextareaWidget = new FormInputTextareaWidget(name);
		final String label = "testLabel";
		assertNotNull(formInputTextareaWidget.addLabel(label));
	}

	@Test
	public void testAddDefaultValue() {
		final String name = "test123";
		final FormInputTextareaWidget formInputTextareaWidget = new FormInputTextareaWidget(name);
		final String defaultValue = "defaultValue";
		assertNotNull(formInputTextareaWidget.addDefaultValue(defaultValue));
	}

	@Test
	public void testAddPlaceholder() {
		final String name = "test123";
		final FormInputTextareaWidget formInputTextareaWidget = new FormInputTextareaWidget(name);
		final String placeholder = "placeholder";
		assertNotNull(formInputTextareaWidget.addPlaceholder(placeholder));
	}

	@Test
	public void testAddId() {
		final String name = "test123";
		final FormInputTextareaWidget formInputTextareaWidget = new FormInputTextareaWidget(name);
		final String id = "id";
		assertNotNull(formInputTextareaWidget.addId(id));
	}

}
