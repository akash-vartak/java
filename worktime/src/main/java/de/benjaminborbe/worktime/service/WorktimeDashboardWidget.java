package de.benjaminborbe.worktime.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.dashboard.api.DashboardWidget;
import de.benjaminborbe.dashboard.api.JavascriptResource;
import de.benjaminborbe.dashboard.api.JavascriptResourceImpl;
import de.benjaminborbe.dashboard.api.RequireJavascriptResource;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.tools.date.DateUtil;
import de.benjaminborbe.worktime.api.Workday;
import de.benjaminborbe.worktime.api.WorktimeService;

@Singleton
public class WorktimeDashboardWidget implements DashboardWidget, RequireJavascriptResource {

	private final static int DEFAULT_DAY_AMOUNT = 5;

	private final Logger logger;

	private final WorktimeService worktimeService;

	private final DateUtil dateUtil;

	@Inject
	public WorktimeDashboardWidget(final Logger logger, final WorktimeService worktimeService, final DateUtil dateUtil) {
		this.logger = logger;
		this.worktimeService = worktimeService;
		this.dateUtil = dateUtil;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		logger.debug("render");

		try {
			final List<Workday> worktimes = worktimeService.getTimes(DEFAULT_DAY_AMOUNT);
			final PrintWriter out = response.getWriter();

			out.println("<table class=\"sortable\">");
			out.println("<tr>");
			out.println("<th>");
			out.println("Day");
			out.println("</th>");
			out.println("<th>");
			out.println("StartTime");
			out.println("</th>");
			out.println("<th>");
			out.println("EndTime");
			out.println("</th>");
			out.println("</tr>");
			for (final Workday workday : worktimes) {
				out.println("<tr>");
				out.println("<td>");
				out.println(dateUtil.dateString(workday.getDate().getTime()));
				out.println("</td>");
				out.println("<td>");
				if (workday.getStart() != null)
					out.println(dateUtil.timeString(workday.getStart().getTime()));
				else
					out.println("-");
				out.println("</td>");
				out.println("<td>");
				if (workday.getEnd() != null)
					out.println(dateUtil.timeString(workday.getEnd().getTime()));
				else
					out.println("-");
				out.println("</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		}
		catch (final StorageException e) {
			final PrintWriter out = response.getWriter();
			e.printStackTrace(out);
		}
	}

	@Override
	public String getTitle() {
		return "Worktime";
	}

	@Override
	public List<JavascriptResource> getJavascriptResource(final HttpServletRequest request,
			final HttpServletResponse response) {
		final String contextPath = request.getContextPath();
		final List<JavascriptResource> result = new ArrayList<JavascriptResource>();
		result.add(new JavascriptResourceImpl(contextPath + "/js/sorttable.js"));
		return result;
	}
}
