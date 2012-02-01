package de.benjaminborbe.weather.gui.servlet;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.website.servlet.WebsiteRedirectServlet;

@Singleton
public class WeatherGuiServlet extends WebsiteRedirectServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String DEFAULT_TARGET = "dashboard";

	@Inject
	public WeatherGuiServlet(final Logger logger) {
		super(logger);
	}

	@Override
	protected String getTarget() {
		return DEFAULT_TARGET;
	}
}
