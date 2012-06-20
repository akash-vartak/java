package de.benjaminborbe.lunch.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.atlassian.confluence.rpc.AuthenticationFailedException;
import com.atlassian.confluence.rpc.InvalidSessionException;
import com.atlassian.confluence.rpc.RemoteException;
import com.atlassian.confluence.rpc.soap.beans.RemotePage;
import com.atlassian.confluence.rpc.soap.beans.RemotePageSummary;
import com.google.inject.Inject;

import net.seibertmedia.kunden.ConfluenceSoapService;
import net.seibertmedia.kunden.ConfluenceSoapServiceServiceLocator;
import de.benjaminborbe.lunch.api.Lunch;
import de.benjaminborbe.lunch.bean.LunchBean;
import de.benjaminborbe.tools.date.DateUtil;
import de.benjaminborbe.tools.html.HtmlUtil;
import de.benjaminborbe.tools.util.ParseException;

public class WikiConnector {

	private final DateUtil dateUtil;

	private final HtmlUtil htmlUtil;

	// https://developer.atlassian.com/display/CONFDEV/Confluence+XML-RPC+and+SOAP+APIs
	@Inject
	public WikiConnector(final DateUtil dateUtil, final HtmlUtil htmlUtil) {
		this.dateUtil = dateUtil;
		this.htmlUtil = htmlUtil;
	}

	public Collection<Lunch> extractLunchs(final String spaceKey, final String username, final String password) throws ServiceException, AuthenticationFailedException,
			RemoteException, java.rmi.RemoteException, ParseException {

		final List<Lunch> result = new ArrayList<Lunch>();

		final ConfluenceSoapServiceServiceLocator serviceLocator = new ConfluenceSoapServiceServiceLocator();
		final ConfluenceSoapService service = serviceLocator.getConfluenceserviceV1();
		final String token = service.login(username, password);
		final RemotePageSummary[] remotePageSummaries = service.getPages(token, spaceKey);
		for (final RemotePageSummary remotePageSummary : remotePageSummaries) {
			if (isLunchPage(remotePageSummary)) {
				final Lunch lunch = createLunch(service, token, remotePageSummary);
				result.add(lunch);
			}
		}
		return result;
	}

	protected Lunch createLunch(final ConfluenceSoapService service, final String token, final RemotePageSummary remotePageSummary) throws ParseException, InvalidSessionException,
			RemoteException, java.rmi.RemoteException {
		final LunchBean lunch = new LunchBean();
		final RemotePage page = service.getPage(token, remotePageSummary.getId());
		final String htmlContent = service.renderContent(token, page.getSpace(), page.getId(), page.getContent());
		lunch.setName(extractLunchName(htmlContent));
		lunch.setSubscribed(extractLunchSubscribed(htmlContent));
		lunch.setDate(extractDate(remotePageSummary.getTitle()));
		return lunch;
	}

	protected boolean extractLunchSubscribed(final String htmlContent) {
		final Document document = Jsoup.parse(htmlContent);
		final Elements tables = document.getElementsByClass("confluenceTable");
		for (final Element table : tables) {
			final Elements tds = table.getElementsByTag("td");
			for (final Element td : tds) {
				if (td.html().indexOf("Benjamin Borbe") != -1) {
					return true;
				}
			}
		}
		return false;
	}

	private String extractLunchName(final String htmlContent) {
		final Document document = Jsoup.parse(htmlContent);
		final Elements elements = document.getElementsByClass("tipMacro");
		for (final Element element : elements) {
			final Elements tds = element.getElementsByTag("td");
			for (final Element td : tds) {
				final String innerHtml = td.html();
				final String result = htmlUtil.filterHtmlTages(innerHtml);
				if (result != null && result.length() > 0) {
					return result;
				}
			}
		}
		return null;
	}

	protected Date extractDate(final String title) throws ParseException {
		final String[] parts = title.split(" ");
		return dateUtil.parseDate(parts[0]);
	}

	protected boolean isLunchPage(final RemotePageSummary remotePageSummary) {
		return remotePageSummary != null && remotePageSummary.getTitle() != null && remotePageSummary.getTitle().matches("\\d+-\\d+-\\d+ Bastians Mittagessen");
	}

}