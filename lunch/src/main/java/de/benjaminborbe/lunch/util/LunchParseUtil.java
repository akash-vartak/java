package de.benjaminborbe.lunch.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import com.google.inject.Inject;

import de.benjaminborbe.tools.html.HtmlUtil;

public class LunchParseUtil {

	private final HtmlUtil htmlUtil;

	private final Logger logger;

	@Inject
	public LunchParseUtil(final Logger logger, final HtmlUtil htmlUtil) {
		this.logger = logger;
		this.htmlUtil = htmlUtil;
	}

	public Collection<String> extractSubscribedUser(final String htmlContent) {
		// logger.debug("htmlContent:\n" + htmlContent);
		final List<String> result = new ArrayList<String>();
		final Document document = Jsoup.parse(htmlContent);
		final Elements tables = document.getElementsByTag("table");
		for (final Element table : tables) {
			if (isSubscriptTable(table)) {
				for (final Element tr : table.getElementsByTag("tr")) {
					final Elements tds = tr.getElementsByTag("td");
					if (!tds.isEmpty()) {
						final String name = tds.get(0).text();
						if (name != null) {
							final String nameTrimed = name.trim();
							if (nameTrimed.length() > 1) {
								logger.debug("found subscription for user: '" + nameTrimed + "'");
								result.add(nameTrimed);
							}
						}
					}
				}
			}
		}
		logger.debug("found " + result.size() + " subscribed users in htmlcontent");
		return result;
	}

	private boolean isSubscriptTable(final Element table) {
		final Elements trs = table.getElementsByTag("tr");
		if (trs != null && !trs.isEmpty()) {
			final Element head = trs.get(0);
			final Elements tds = head.getElementsByTag("th");
			if (tds != null && !tds.isEmpty()) {
				final String text = tds.get(0).text();
				return text != null && text.contains("Teilnehmer");
			}
		}
		return false;
	}

	public boolean extractLunchSubscribed(final String content, final String fullname) {
		return content.indexOf(fullname) != -1;
		// final Document document = Jsoup.parse(htmlContent);
		// final Elements tables = document.getElementsByClass("confluenceTable");
		// for (final Element table : tables) {
		// final Elements tds = table.getElementsByTag("td");
		// for (final Element td : tds) {
		// if (td.html().indexOf(fullname) != -1) {
		// return true;
		// }
		// }
		// }
		// return false;
	}

	public String extractLunchName(final String htmlContent) {
		// final String content = htmlContent.replaceAll("ac:", "ac");
		final Document document = Jsoup.parse(htmlContent);
		// System.err.println(document.toString());
		{
			final Elements elements = document.getElementsByClass("tipMacro");
			for (final Element element : elements) {
				for (final Element td : element.getElementsByTag("p")) {
					final String innerHtml = td.html();
					final String result = htmlUtil.filterHtmlTages(innerHtml);
					if (result != null && result.length() > 0) {
						return result;
					}
				}
			}
		}
		{
			final int pos = htmlContent.indexOf("ac:name=\"tip\"");
			final int pos2 = htmlContent.indexOf("INLINE", pos);
			final int pstart = htmlContent.indexOf("<p>", pos2);
			final int pend = htmlContent.indexOf("</p>", pos2);
			final String result = htmlUtil.filterHtmlTages(htmlContent.substring(pstart, pend));
			if (result != null && result.length() > 0) {
				return result;
			}
		}

		logger.debug("extractLunchName failed " + htmlContent);
		return null;
	}
}
