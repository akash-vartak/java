package de.benjaminborbe.calendar.gui.service;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.dashboard.api.DashboardContentWidget;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.website.br.BrWidget;
import de.benjaminborbe.website.util.ListWidget;

@Singleton
public class CalendarGuiDashboardWidget implements DashboardContentWidget {

	private final CalendarUtil calendarUtil;

	private final TimeZoneUtil timeZoneUtil;

	@Inject
	public CalendarGuiDashboardWidget(final Logger logger, final CalendarUtil calendarUtil, final TimeZoneUtil timeZoneUtil) {
		this.calendarUtil = calendarUtil;
		this.timeZoneUtil = timeZoneUtil;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException, PermissionDeniedException {
		final ListWidget widgets = new ListWidget();
		final Calendar now = calendarUtil.now(timeZoneUtil.getUTCTimeZone());
		widgets.add("today: " + calendarUtil.toDateString(now));
		widgets.add(new BrWidget());
		widgets.add("now: " + calendarUtil.toDateTimeString(now));
		widgets.add(new BrWidget());
		widgets.add("time: " + calendarUtil.getTime(now));
		widgets.add(new BrWidget());
		widgets.add("weekday: " + calendarUtil.getWeekday(now));
		widgets.render(request, response, context);
	}

	@Override
	public String getTitle() {
		return "CalendarWidget";
	}

	@Override
	public long getPriority() {
		return 1;
	}

}
