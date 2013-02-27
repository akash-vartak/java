package de.benjaminborbe.dhl.gui.servlet;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.dhl.gui.util.DhlWebsiteRedirectServlet;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;

@Singleton
public class DhlGuiServlet extends DhlWebsiteRedirectServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	@Inject
	public DhlGuiServlet(
			final Logger logger,
			final AuthenticationService authenticationService,
			final UrlUtil urlUtil,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final Provider<HttpContext> httpContextProvider,
			final AuthorizationService authorizationService) {
		super(logger, urlUtil, authenticationService, calendarUtil, timeZoneUtil, httpContextProvider, authorizationService);
	}

	@Override
	protected String getTarget() {
		return "/dhl/list";
	}
}
