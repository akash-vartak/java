package de.benjaminborbe.bookmark.dao;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.tools.dao.DaoBase;
import de.benjaminborbe.tools.util.IdGenerator;

@Singleton
public class BookmarkDaoImpl extends DaoBase<BookmarkBean> implements BookmarkDao {

	private static final String DEFAULT_DESCRIPTION = "-";

	@Inject
	public BookmarkDaoImpl(final Logger logger, final IdGenerator idGenerator, final Provider<BookmarkBean> provider) {
		super(logger, idGenerator, provider);
	}

	@Override
	protected void init() {
		// internal
		save(createBookmark("/bb/monitoring", "Local - BB - Monitoring"));
		save(createBookmark("/bb/gwt/Home.html", "Local - BB - GWT"));
		save(createBookmark("/bb/search", "Local - BB - Search"));

		// extern
		save(createBookmark("https://console.aws.amazon.com/ec2/home", "Amazon EC2"));

		// local
		save(createBookmark("http://localhost:8180/manager/html/list", "Local - Tomcat Manager"));
		save(createBookmark("http://phpmyadmin/", "Local - phpMyAdmin"));
		save(createBookmark("http://0.0.0.0:8161/admin/queues.jsp", "Local - ActiveMQ - JMS"));

		// 20ft devel
		save(createBookmark("/bb/twentyfeetperformance", "Twentyfeet - Devel - Performance"));
		save(createBookmark("http://localhost:8180/app/", "Twentyfeet - Devel"));
		save(createBookmark("http://localhost:8180/app/admin", "Twentyfeet - Devel - Admin"));
		save(createBookmark("http://localhost:8180/app/?log_level=DEBUG", "Twentyfeet - Devel - App with Debug"));
		save(createBookmark("http://127.0.0.1:8888/app/Home.html?gwt.codesvr=127.0.0.1:9997",
				"Twentyfeet - Devel - App in Developermode"));

		// 20ft live
		save(createBookmark("https://www.twentyfeet.com/", "Twentyfeet - Live"));
		save(createBookmark("https://www.twentyfeet.com/admin/queues.jsp", "Twentyfeet - Live - ActiveMQ - JMS"));
		save(createBookmark("https://central.twentyfeet.com/phpmyadmin/", "Twentyfeet - Live - phpMyadmin"));
		save(createBookmark("https://kunden.seibert-media.net/display/20ft", "Twentyfeet - Live - Wiki"));

		// 20ft test
		save(createBookmark("https://test.twentyfeet.com/", "Twentyfeet - Test"));
		save(createBookmark("https://test.twentyfeet.com/admin/queues.jsp", "Twentyfeet - Test - ActiveMQ - JMS"));

		// seibert-media
		save(createBookmark("https://timetracker.rp.seibert-media.net/", "Seibert-Media - Timetracker"));
		save(createBookmark("https://confluence.rp.seibert-media.net/", "Seibert-Media - Wiki"));
		save(createBookmark("https://hudson.rp.seibert-media.net/", "Seibert-Media - Hudson / Jenkins"));
		save(createBookmark("https://micro.rp.seibert-media.net", "Seibert-Media - Microblog"));
		save(createBookmark("http://nexus.rp.seibert-media.net/", "Seibert-Media - Nexus"));

		// Movie
		save(createBookmark("http://www.cinestar.de/de/kino/mainz-cinestar/", "Movie - Kino - Mainz - Cinestar"));
		save(createBookmark("http://www.filmstarts.de/", "Movie - Review - Filmstarts"));
		save(createBookmark("http://rogerebert.suntimes.com/", "Movie - Review - Roger Ebert"));
		save(createBookmark("http://imdb.com/", "Movie - Review - Imdb"));
	}

	protected BookmarkBean createBookmark(final String url, final String name) {
		return createBookmark(url, name, DEFAULT_DESCRIPTION);
	}

	protected BookmarkBean createBookmark(final String url, final String name, final String description) {
		final BookmarkBean bookmark = create();
		bookmark.setUrl(url);
		bookmark.setName(name);
		bookmark.setDescription(description);
		return bookmark;
	}

}
