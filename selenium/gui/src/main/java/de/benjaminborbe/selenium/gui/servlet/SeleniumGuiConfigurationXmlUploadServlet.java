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
import de.benjaminborbe.selenium.configuration.xml.api.SeleniumConfigurationXmlService;
import de.benjaminborbe.selenium.configuration.xml.api.SeleniumConfigurationXmlServiceException;
import de.benjaminborbe.selenium.gui.SeleniumGuiConstants;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.form.FormInputSubmitWidget;
import de.benjaminborbe.website.form.FormInputTextareaWidget;
import de.benjaminborbe.website.form.FormMethod;
import de.benjaminborbe.website.form.FormWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.util.RedirectWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class SeleniumGuiConfigurationXmlUploadServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Selenium - Configuration - XML - Upload";

	private final Logger logger;

	private final AuthenticationService authenticationService;

	private final SeleniumConfigurationXmlService seleniumConfigurationXmlService;

	@Inject
	public SeleniumGuiConfigurationXmlUploadServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final AuthenticationService authenticationService,
		final NavigationWidget navigationWidget,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final AuthorizationService authorizationService,
		final CacheService cacheService,
		final SeleniumConfigurationXmlService seleniumConfigurationXmlService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.logger = logger;
		this.authenticationService = authenticationService;
		this.seleniumConfigurationXmlService = seleniumConfigurationXmlService;
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
			widgets.add(new H1Widget(getTitle()));

			final String xml = request.getParameter(SeleniumGuiConstants.PARAMETER_CONFIGURATION_XML);
			if (xml != null) {
				final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
				seleniumConfigurationXmlService.addXml(sessionIdentifier, xml);
				return new RedirectWidget(request.getContextPath() + "/" + SeleniumGuiConstants.NAME + SeleniumGuiConstants.URL_CONFIGURATION_LIST);
			}

			final FormWidget form = new FormWidget().addMethod(FormMethod.POST);
			form.addFormInputWidget(new FormInputTextareaWidget(SeleniumGuiConstants.PARAMETER_CONFIGURATION_XML).addLabel("Xml:").addDefaultValue(getDefaultXml()));
			form.addFormInputWidget(new FormInputSubmitWidget("upload"));
			widgets.add(form);

			return widgets;
		} catch (AuthenticationServiceException e) {
			return new ExceptionWidget(e);
		} catch (SeleniumConfigurationXmlServiceException e) {
			return new ExceptionWidget(e);
		}
	}

	private String getDefaultXml() {
		final StringBuilder sb = new StringBuilder();
		sb.append("<config>\n");
		sb.append("  <id>test</id>\n");
		sb.append("  <name>Test Configuration</name>\n");
		sb.append("  <actions>\n");
		sb.append("    <action name=\"GetUrl\">\n");
		sb.append("      <message>test-message</message>\n");
		sb.append("      <url>http://www.benjamin-borbe.de</url>\n");
		sb.append("    </action>\n");
		sb.append("  </actions>\n");
		sb.append("</config>\n");
		return sb.toString();
	}
}
