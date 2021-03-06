package de.benjaminborbe.websearch.core.config;

import de.benjaminborbe.configuration.api.ConfigurationDescription;

import java.util.Collection;

public interface WebsearchConfig {

	Collection<ConfigurationDescription> getConfigurations();

	Integer getRefreshLimit();

	boolean getCronEnabled();

	boolean saveImages();

	int minImageLongSide();

	int minImageShortSide();
}
