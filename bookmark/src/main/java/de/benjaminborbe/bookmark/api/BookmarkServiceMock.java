package de.benjaminborbe.bookmark.api;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BookmarkServiceMock implements BookmarkService {

	@Inject
	public BookmarkServiceMock() {
	}

	@Override
	public List<Bookmark> getBookmarks() {
		
		return null;
	}

	@Override
	public List<Bookmark> searchBookmarks(final String[] words) {
		
		return null;
	}

}
