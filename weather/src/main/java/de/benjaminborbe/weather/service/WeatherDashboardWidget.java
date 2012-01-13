package de.benjaminborbe.weather.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.dashboard.api.DashboardContentWidget;
import de.benjaminborbe.html.api.HttpContext;

@Singleton
public class WeatherDashboardWidget implements DashboardContentWidget {

	@Inject
	public WeatherDashboardWidget(final Logger logger) {
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		final PrintWriter out = response.getWriter();
		out.println("weather");
	}

	@Override
	public String getTitle() {
		return "WeatherWidget";
	}

	@Override
	public long getPriority() {
		return 1;
	}
}
