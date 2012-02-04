package de.benjaminborbe.website.table;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.benjaminborbe.html.api.HTML;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;

public class TableCell implements Widget {

	private HTML content;

	public TableCell setContent(final HTML content) {
		this.content = content;
		return this;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		final PrintWriter out = response.getWriter();
		out.println("<td>");
		if (content != null) {
			content.render(request, response, context);
		}
		out.println("</td>");
	}
}