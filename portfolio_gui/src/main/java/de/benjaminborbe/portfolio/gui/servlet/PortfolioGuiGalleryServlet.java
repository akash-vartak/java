package de.benjaminborbe.portfolio.gui.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.gallery.api.GalleryCollection;
import de.benjaminborbe.gallery.api.GalleryCollectionIdentifier;
import de.benjaminborbe.gallery.api.GalleryEntry;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.gallery.api.GalleryServiceException;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.portfolio.gui.PortfolioGuiConstants;
import de.benjaminborbe.portfolio.gui.util.PortfolioGuiGalleryCollectionComparator;
import de.benjaminborbe.portfolio.gui.util.PortfolioGuiGalleryEntryComparator;
import de.benjaminborbe.portfolio.gui.util.PortfolioGuiLinkFactory;
import de.benjaminborbe.portfolio.gui.widget.PortfolioLayoutWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.servlet.WebsiteWidgetServlet;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.RedirectWidget;

@Singleton
public class PortfolioGuiGalleryServlet extends WebsiteWidgetServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private final Provider<PortfolioLayoutWidget> portfolioWidgetProvider;

	private final GalleryService galleryService;

	private final AuthenticationService authenticationService;

	private final PortfolioGuiGalleryCollectionComparator galleryComparator;

	private final UrlUtil urlUtil;

	private final PortfolioGuiLinkFactory portfolioLinkFactory;

	private final PortfolioGuiGalleryEntryComparator portfolioGuiGalleryEntryComparator;

	@Inject
	public PortfolioGuiGalleryServlet(
			final Logger logger,
			final UrlUtil urlUtil,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final Provider<HttpContext> httpContextProvider,
			final AuthenticationService authenticationService,
			final Provider<PortfolioLayoutWidget> portfolioWidgetProvider,
			final GalleryService galleryService,
			final PortfolioGuiLinkFactory portfolioLinkFactory,
			final PortfolioGuiGalleryCollectionComparator galleryComparator,
			final PortfolioGuiGalleryEntryComparator portfolioGuiGalleryEntryComparator,
			final AuthorizationService authorizationService) {
		super(logger, urlUtil, calendarUtil, timeZoneUtil, httpContextProvider, authenticationService, authorizationService);
		this.authenticationService = authenticationService;
		this.portfolioWidgetProvider = portfolioWidgetProvider;
		this.galleryService = galleryService;
		this.galleryComparator = galleryComparator;
		this.urlUtil = urlUtil;
		this.portfolioLinkFactory = portfolioLinkFactory;
		this.portfolioGuiGalleryEntryComparator = portfolioGuiGalleryEntryComparator;
	}

	@Override
	public Widget createWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		try {
			final String galleryId = urlUtil.parseId(request, PortfolioGuiConstants.PARAMETER_GALLERY_ID);
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final GalleryCollectionIdentifier galleryCollectionIdentifier = galleryService.createCollectionIdentifier(galleryId);
			final GalleryCollection galleryCollection = galleryService.getCollectionShared(sessionIdentifier, galleryCollectionIdentifier);
			if (galleryCollection == null) {
				final String target = portfolioLinkFactory.createGalleryUrl(request, getDefaultGalleryCollection(request, sessionIdentifier));
				return new RedirectWidget(target);
			}
			else {
				final PortfolioLayoutWidget portfolioWidget = portfolioWidgetProvider.get();
				portfolioWidget.addTitle(galleryCollection.getName() + " - Benjamin Borbe");
				portfolioWidget.addContent(new H1Widget(galleryCollection.getName()));
				final List<GalleryEntry> entries = Lists.newArrayList(galleryService.getEntriesShared(sessionIdentifier, galleryCollection.getId()));
				Collections.sort(entries, portfolioGuiGalleryEntryComparator);
				portfolioWidget.setGalleryEntries(entries);
				return portfolioWidget;
			}
		}
		catch (final AuthenticationServiceException e) {
			return new ExceptionWidget(e);
		}
		catch (final GalleryServiceException e) {
			return new ExceptionWidget(e);
		}
	}

	@Override
	public boolean isLoginRequired() {
		return false;
	}

	@Override
	public boolean isAdminRequired() {
		return false;
	}

	private GalleryCollection getDefaultGalleryCollection(final HttpServletRequest request, final SessionIdentifier sessionIdentifier) throws GalleryServiceException {
		final List<GalleryCollection> galleries = new ArrayList<GalleryCollection>(galleryService.getCollectionsShared(sessionIdentifier));
		if (galleries.size() > 0) {
			Collections.sort(galleries, galleryComparator);
			return galleries.get(0);
		}
		else {
			return null;
		}
	}
}
