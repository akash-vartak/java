package de.benjaminborbe.timetracker.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TimetrackerServlet extends HttpServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private final Logger logger;

	@Inject
	public TimetrackerServlet(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void service(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		logger.debug("service");
		response.setContentType("text/plain");
		final PrintWriter out = response.getWriter();
		out.println("Timetracker!");
	}
}
