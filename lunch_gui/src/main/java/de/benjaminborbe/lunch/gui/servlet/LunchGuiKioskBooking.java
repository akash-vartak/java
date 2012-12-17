package de.benjaminborbe.lunch.gui.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.lunch.api.LunchService;
import de.benjaminborbe.lunch.api.LunchServiceException;
import de.benjaminborbe.lunch.gui.LunchGuiConstants;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.form.FormCheckboxWidget;
import de.benjaminborbe.website.form.FormInputSubmitWidget;
import de.benjaminborbe.website.form.FormWidget;
import de.benjaminborbe.website.link.LinkWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;

@Singleton
public class LunchGuiKioskBooking extends LunchGuiHtmlServlet {

	private static final long serialVersionUID = -7698261881382004351L;

	private static final String TITLE = "Lunch - Kiosk - Booking";

	private final Logger logger;

	private final LunchService lunchService;

	private final CalendarUtil calendarUtil;

	private final AuthenticationService authenticationService;

	private final TimeZoneUtil timeZoneUtil;

	@Inject
	public LunchGuiKioskBooking(
			final Logger logger,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final ParseUtil parseUtil,
			final NavigationWidget navigationWidget,
			final AuthenticationService authenticationService,
			final AuthorizationService authorizationService,
			final Provider<HttpContext> httpContextProvider,
			final UrlUtil urlUtil,
			final LunchService lunchService) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil);
		this.logger = logger;
		this.lunchService = lunchService;
		this.calendarUtil = calendarUtil;
		this.timeZoneUtil = timeZoneUtil;
		this.authenticationService = authenticationService;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException,
			PermissionDeniedException, RedirectException, LoginRequiredException {
		try {
			logger.trace("printContent");
			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(getTitle()));

			final String[] selectedUsers = request.getParameterValues(LunchGuiConstants.PARAMETER_BOOKING_USER);
			if (selectedUsers != null && selectedUsers.length > 0) {
				logger.info("book user: " + StringUtils.join(selectedUsers, ","));
			}

			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final List<String> users = new ArrayList<String>(lunchService.getSubscribeUser(sessionIdentifier, calendarUtil.today(timeZoneUtil.getUTCTimeZone())));
			Collections.sort(users);

			final FormWidget form = new FormWidget().addId("bookings");

			for (final String user : users) {
				form.addFormInputWidget(new FormCheckboxWidget(LunchGuiConstants.PARAMETER_BOOKING_USER).addLabel(user).addValue(user).setCheckedDefault(true));
			}
			form.addFormInputWidget(new FormInputSubmitWidget("buchen"));
			widgets.add(form);
			widgets.add(new LinkWidget("javascript:toggle()", "toggle"));

			return widgets;
		}
		catch (final AuthenticationServiceException e) {
			logger.debug(e.getClass().getName(), e);
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
		catch (final LunchServiceException e) {
			logger.debug(e.getClass().getName(), e);
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
	}
}
