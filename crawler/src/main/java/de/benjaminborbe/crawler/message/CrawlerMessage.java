package de.benjaminborbe.crawler.message;

import java.net.URL;

public class CrawlerMessage {

	private URL url;

	private int timeout;

	public CrawlerMessage() {
	}

	public CrawlerMessage(final URL url, final int timeout) {
		this.url = url;
		this.timeout = timeout;
	}

	public URL getUrl() {
		return url;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setUrl(final URL url) {
		this.url = url;
	}

	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

}