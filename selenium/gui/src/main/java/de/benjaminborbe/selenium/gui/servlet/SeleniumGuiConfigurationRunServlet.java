package de.benjaminborbe.selenium.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.selenium.api.SeleniumConfiguration;
import de.benjaminborbe.selenium.api.SeleniumConfigurationIdentifier;
import de.benjaminborbe.selenium.api.SeleniumExecutionProtocol;
import de.benjaminborbe.selenium.api.SeleniumService;
import de.benjaminborbe.selenium.api.SeleniumServiceException;
import de.benjaminborbe.selenium.gui.SeleniumGuiConstants;
import de.benjaminborbe.selenium.gui.widget.SeleniumGuiExecuteWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class SeleniumGuiConfigurationRunServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Selenium - Configuration - Run";

	private final Logger logger;

	private final AuthenticationService authenticationService;

	private final SeleniumService seleniumService;

	@Inject
	public SeleniumGuiConfigurationRunServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final AuthenticationService authenticationService,
		final NavigationWidget navigationWidget,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final AuthorizationService authorizationService,
		final CacheService cacheService, final SeleniumService seleniumService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.logger = logger;
		this.authenticationService = authenticationService;
		this.seleniumService = seleniumService;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected Widget createContentWidget(
		final HttpServletRequest request, final HttpServletResponse response, final HttpContext context
	) throws IOException, PermissionDeniedException, RedirectException, LoginRequiredException {
		try {
			logger.trace("printContent");
			final ListWidget widgets = new ListWidget();

			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final SeleniumConfigurationIdentifier seleniumConfigurationIdentifier = seleniumService.createSeleniumConfigurationIdentifier(request.getParameter(SeleniumGuiConstants.PARAMETER_CONFIGURATION_ID));
			final SeleniumConfiguration seleniumConfiguration = seleniumService.getConfiguration(sessionIdentifier, seleniumConfigurationIdentifier);
			if (seleniumConfiguration == null) {
				widgets.add("configuration not found");
				return widgets;
			}
			widgets.add(new H1Widget(getTitle() + " - " + seleniumConfiguration.getName()));

			final SeleniumExecutionProtocol seleniumExecutionProtocol = seleniumService.execute(sessionIdentifier, seleniumConfigurationIdentifier);
			widgets.add(new SeleniumGuiExecuteWidget(seleniumExecutionProtocol));

			return widgets;
		} catch (SeleniumServiceException e) {
			return new ExceptionWidget(e);
		} catch (AuthenticationServiceException e) {
			return new ExceptionWidget(e);
		}
	}
}
