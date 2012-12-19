package de.benjaminborbe.search.dao;

import de.benjaminborbe.api.IdentifierBuilder;

public class SearchQueryHistoryIdentifierBuilder implements IdentifierBuilder<String, SearchQueryHistoryIdentifier> {

	@Override
	public SearchQueryHistoryIdentifier buildIdentifier(final String value) {
		return new SearchQueryHistoryIdentifier(value);
	}

}
