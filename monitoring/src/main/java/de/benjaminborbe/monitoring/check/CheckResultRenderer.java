package de.benjaminborbe.monitoring.check;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.benjaminborbe.html.api.HTML;
import de.benjaminborbe.html.api.HttpContext;

public class CheckResultRenderer implements HTML {

	private final CheckResult checkResult;

	public CheckResultRenderer(final CheckResult checkResult) {
		this.checkResult = checkResult;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		final PrintWriter out = response.getWriter();
		if (checkResult.isSuccess()) {
			out.print("[<span class=\"checkResultOk\">OK</span>] ");
		}
		else {
			out.print("[<span class=\"checkResultFail\">FAIL</span>] ");
		}
		out.print(checkResult.getName());
		out.print(" - ");
		out.print(checkResult.getMessage());
	}

}
