package de.benjaminborbe.websearch.page;

import java.net.MalformedURLException;
import java.net.URL;

import de.benjaminborbe.api.IdentifierBuilder;
import de.benjaminborbe.websearch.api.PageIdentifier;

public class PageIdentifierBuilder implements IdentifierBuilder<String, PageIdentifier> {

	@Override
	public PageIdentifier buildIdentifier(final String value) throws MalformedURLException {
		return new PageIdentifier(new URL(value));
	}

}
