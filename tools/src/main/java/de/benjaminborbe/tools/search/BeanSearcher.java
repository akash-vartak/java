package de.benjaminborbe.tools.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BeanSearcher<B> {

	private final class Match {

		private final B bean;

		private final int counter;

		public Match(final B bean, final int counter) {
			this.bean = bean;
			this.counter = counter;
		}

		public B getB() {
			return bean;
		}

		public int getCounter() {
			return counter;
		}
	}

	private final class MatchComparator implements Comparator<Match> {

		@Override
		public int compare(final Match a, final Match b) {
			if (a.getCounter() > b.getCounter()) {
				return -1;
			}
			if (a.getCounter() < b.getCounter()) {
				return 1;
			}
			return 0;
		}
	}

	private static final int SEARCH_TERM_MIN_LENGTH = 2;

	public List<B> search(final List<B> beans, final String[] parts) {
		final List<Match> matches = new ArrayList<Match>();
		for (final B bean : beans) {
			final int counter = match(bean, parts);
			if (counter > 0) {
				final Match match = new Match(bean, counter);
				matches.add(match);
			}
		}
		Collections.sort(matches, new MatchComparator());
		final List<B> result = new ArrayList<B>();
		for (final Match match : matches) {
			result.add(match.getB());
		}
		return result;
	}

	private int match(final B bean, final String... searchTerms) {
		int counter = 0;
		for (final String searchTerm : searchTerms) {
			if (searchTerm != null && searchTerm.length() >= SEARCH_TERM_MIN_LENGTH) {
				counter += match(bean, searchTerm);
			}
		}
		return counter;
	}

	private int match(final B bean, final String search) {
		final String searchLower = search.toLowerCase();
		int counter = 0;
		for (final String value : getSearchValues(bean)) {
			final String valueLower = value.toLowerCase();
			int pos = -1;
			while ((pos = searchLower.indexOf(valueLower, pos + 1)) != -1) {
				return counter++;
			}
		}
		return counter;
	}

	protected abstract Collection<String> getSearchValues(final B bean);

}
