package de.benjaminborbe.tools.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.tools.date.CalendarUtil;

@Singleton
public class HttpLastModifiedFilter extends HttpFilter {

	// 5 Minuten Cache
	private static final long TIMEOUT = 5 * 60 * 1000;

	private final CalendarUtil calendarUtil;

	@Inject
	public HttpLastModifiedFilter(final Logger logger, final CalendarUtil calendarUtil) {
		super(logger);
		this.calendarUtil = calendarUtil;
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		try {
			response.setHeader("Cache-Control", "public, max-age=300");
			response.setDateHeader("Expires", calendarUtil.getTime() + TIMEOUT);
		}
		finally {
			filterChain.doFilter(request, response);
		}
	}
}
