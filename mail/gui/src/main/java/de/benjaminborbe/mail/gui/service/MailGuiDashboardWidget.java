package de.benjaminborbe.mail.gui.service;

import de.benjaminborbe.dashboard.api.DashboardContentWidget;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.mail.gui.MailGuiConstants;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Singleton
public class MailGuiDashboardWidget implements DashboardContentWidget {

	private final Logger logger;

	@Inject
	public MailGuiDashboardWidget(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		logger.trace("render");
		final PrintWriter out = response.getWriter();
		out.println("0 new mails");
	}

	@Override
	public String getTitle() {
		return "Mail";
	}

	@Override
	public long getPriority() {
		return 1;
	}

	@Override
	public boolean isAdminRequired() {
		return false;
	}

	@Override
	public String getName() {
		return MailGuiConstants.NAME;
	}

}
