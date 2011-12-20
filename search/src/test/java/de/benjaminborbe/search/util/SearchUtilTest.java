package de.benjaminborbe.search.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class SearchUtilTest {

	@Test
	public void BookmarkServiceImpl() {
		final SearchUtil bookmarkService = new SearchUtil();
		assertEquals(StringUtils.join(Arrays.asList("a").toArray(), ","),
				StringUtils.join(bookmarkService.buildSearchParts("a"), ","));
		assertEquals(StringUtils.join(Arrays.asList("a", "b").toArray(), ","),
				StringUtils.join(bookmarkService.buildSearchParts("a b"), ","));
		assertEquals(StringUtils.join(Arrays.asList("a", "b", "c").toArray(), ","),
				StringUtils.join(bookmarkService.buildSearchParts("a b - c"), ","));
		assertEquals(StringUtils.join(Arrays.asList("a", "b", "c", "hello").toArray(), ","),
				StringUtils.join(bookmarkService.buildSearchParts("a b - c  Hello"), ","));
	}
}
