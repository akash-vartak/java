package de.benjaminborbe.websearch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.index.api.IndexSearchResult;
import de.benjaminborbe.index.api.IndexSearcherService;
import de.benjaminborbe.search.api.SearchResult;
import de.benjaminborbe.search.api.SearchResultImpl;
import de.benjaminborbe.search.api.SearchServiceComponent;
import de.benjaminborbe.websearch.WebsearchConstants;

@Singleton
public class WebsearchSearchServiceComponent implements SearchServiceComponent {

	private static final String SEARCH_TYPE = "Web";

	private final Logger logger;

	private final IndexSearcherService indexSearcherService;

	@Inject
	public WebsearchSearchServiceComponent(final Logger logger, final IndexSearcherService indexSearcherService) {
		this.logger = logger;
		this.indexSearcherService = indexSearcherService;
	}

	@Override
	public List<SearchResult> search(final SessionIdentifier sessionIdentifier, final String query, final int maxResults, final String... words) {
		logger.trace("search");
		final List<SearchResult> result = new ArrayList<SearchResult>();
		final List<IndexSearchResult> indexResults = indexSearcherService.search(WebsearchConstants.INDEX, StringUtils.join(words, " "));
		for (final IndexSearchResult indexResult : indexResults) {
			if (result.size() < maxResults) {
				result.add(map(indexResult));
				return result;
			}
		}
		return result;
	}

	protected SearchResult map(final IndexSearchResult indexResult) {
		final String title = indexResult.getTitle();
		final String url = indexResult.getURL();
		final String description = indexResult.getContent();
		// TODO bborbe matchCounter bestimmen
		final int matchCounter = 1;
		return new SearchResultImpl(SEARCH_TYPE, matchCounter, title, url, description);
	}

	@Override
	public String getName() {
		return SEARCH_TYPE;
	}
}
