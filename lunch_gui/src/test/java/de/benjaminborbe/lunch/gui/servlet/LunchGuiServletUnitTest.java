package de.benjaminborbe.lunch.gui.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import com.google.inject.Provider;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.lunch.api.Lunch;
import de.benjaminborbe.lunch.api.LunchService;
import de.benjaminborbe.lunch.gui.LunchGuiConstants;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.DateUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.guice.ProviderAdapter;
import de.benjaminborbe.tools.mock.EnumerationEmpty;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.servlet.RedirectUtil;

public class LunchGuiServletUnitTest {

	@Test
	public void testService() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HttpServletResponse response = EasyMock.createMock(HttpServletResponse.class);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		final StringWriter sw = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(sw);
		EasyMock.expect(response.getWriter()).andReturn(printWriter).anyTimes();
		EasyMock.replay(response);

		final String sessionId = "324908234890";
		final HttpSession session = EasyMock.createMock(HttpSession.class);
		EasyMock.expect(session.getId()).andReturn(sessionId).anyTimes();
		EasyMock.replay(session);

		final HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getContextPath()).andReturn("/path").anyTimes();
		EasyMock.expect(request.getSession()).andReturn(session).anyTimes();
		EasyMock.expect(request.getScheme()).andReturn("http").anyTimes();
		EasyMock.expect(request.getServerName()).andReturn("localhost").anyTimes();
		EasyMock.expect(request.getRequestURI()).andReturn("/path").anyTimes();
		EasyMock.expect(request.getParameterNames()).andReturn(new EnumerationEmpty<String>()).anyTimes();
		EasyMock.expect(request.getParameter(LunchGuiConstants.PARAMETER_FULLNAME)).andReturn(null).anyTimes();
		EasyMock.replay(request);

		final TimeZone timeZone = EasyMock.createMock(TimeZone.class);
		EasyMock.replay(timeZone);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.expect(timeZoneUtil.getUTCTimeZone()).andReturn(timeZone).anyTimes();
		EasyMock.replay(timeZoneUtil);

		final long startTime = 42l;
		final long endTime = 1337l;

		final Calendar calendar = EasyMock.createMock(Calendar.class);
		EasyMock.expect(calendar.getTimeInMillis()).andReturn(startTime);
		EasyMock.expect(calendar.getTimeInMillis()).andReturn(endTime);
		EasyMock.replay(calendar);

		final CalendarUtil calendarUtil = EasyMock.createMock(CalendarUtil.class);
		EasyMock.expect(calendarUtil.now(timeZone)).andReturn(calendar).anyTimes();
		EasyMock.replay(calendarUtil);

		final ParseUtil parseUtil = EasyMock.createMock(ParseUtil.class);
		EasyMock.expect(parseUtil.parseLong(String.valueOf(startTime), endTime)).andReturn(startTime);
		EasyMock.replay(parseUtil);

		final Map<String, String> data = new HashMap<String, String>();

		final HttpContext httpContext = EasyMock.createMock(HttpContext.class);
		EasyMock.expect(httpContext.getData()).andReturn(data).anyTimes();
		EasyMock.replay(httpContext);

		final NavigationWidget navigationWidget = EasyMock.createMock(NavigationWidget.class);
		navigationWidget.render(request, response, httpContext);
		EasyMock.replay(navigationWidget);

		final Provider<HttpContext> httpContextProvider = new ProviderAdapter<HttpContext>(httpContext);

		final SessionIdentifier sessionIdentifier = EasyMock.createMock(SessionIdentifier.class);
		EasyMock.replay(sessionIdentifier);

		final UserIdentifier userIdentifier = EasyMock.createMock(UserIdentifier.class);
		EasyMock.replay(userIdentifier);

		final String fullname = "Foo Bar";
		final AuthenticationService authenticationService = EasyMock.createMock(AuthenticationService.class);
		EasyMock.expect(authenticationService.isLoggedIn(EasyMock.anyObject(SessionIdentifier.class))).andReturn(true).anyTimes();
		EasyMock.expect(authenticationService.createSessionIdentifier(request)).andReturn(sessionIdentifier).anyTimes();
		EasyMock.expect(authenticationService.getCurrentUser(sessionIdentifier)).andReturn(userIdentifier).anyTimes();
		EasyMock.expect(authenticationService.getFullname(sessionIdentifier, userIdentifier)).andReturn(fullname).anyTimes();
		EasyMock.replay(authenticationService);

		final RedirectUtil redirectUtil = EasyMock.createMock(RedirectUtil.class);
		EasyMock.replay(redirectUtil);

		final UrlUtil urlUtil = EasyMock.createMock(UrlUtil.class);
		EasyMock.replay(urlUtil);

		final DateUtil dateUtil = EasyMock.createMock(DateUtil.class);
		EasyMock.replay(dateUtil);

		final LunchService lunchService = EasyMock.createMock(LunchService.class);
		EasyMock.expect(lunchService.getLunchs(sessionIdentifier, fullname)).andReturn(new HashSet<Lunch>());
		EasyMock.replay(lunchService);

		final AuthorizationService authorizationService = EasyMock.createMock(AuthorizationService.class);
		authorizationService.expectAdminRole(sessionIdentifier);
		EasyMock.expect(authorizationService.hasAdminRole(sessionIdentifier)).andReturn(true);
		EasyMock.replay(authorizationService);

		final CacheService cacheService = EasyMock.createMock(CacheService.class);
		EasyMock.expect(cacheService.get("hostname")).andReturn("localhost").anyTimes();
		EasyMock.replay(cacheService);

		final LunchGuiServlet lunchServlet = new LunchGuiServlet(logger, calendarUtil, timeZoneUtil, parseUtil, authenticationService, navigationWidget, httpContextProvider,
				redirectUtil, urlUtil, lunchService, dateUtil, authorizationService, cacheService);

		lunchServlet.service(request, response);
		final String content = sw.getBuffer().toString();
		assertNotNull(content);
		assertTrue(content.indexOf("<h1>Lunch</h1>") != -1);
	}
}
