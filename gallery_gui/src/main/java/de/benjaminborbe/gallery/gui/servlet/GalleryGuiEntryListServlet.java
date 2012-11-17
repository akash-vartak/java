package de.benjaminborbe.gallery.gui.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.gallery.api.GalleryCollectionIdentifier;
import de.benjaminborbe.gallery.api.GalleryEntry;
import de.benjaminborbe.gallery.api.GalleryEntryIdentifier;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.gallery.api.GalleryServiceException;
import de.benjaminborbe.gallery.gui.GalleryGuiConstants;
import de.benjaminborbe.gallery.gui.util.GalleryGuiLinkFactory;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.MapParameter;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.link.LinkRelativWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.servlet.RedirectUtil;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.util.UlWidget;
import de.benjaminborbe.website.widget.ImageWidget;

@Singleton
public class GalleryGuiEntryListServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Gallery - Images";

	private final GalleryService galleryService;

	private final UrlUtil urlUtil;

	private final Logger logger;

	private final GalleryGuiLinkFactory galleryGuiLinkFactory;

	@Inject
	public GalleryGuiEntryListServlet(
			final Logger logger,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final ParseUtil parseUtil,
			final AuthenticationService authenticationService,
			final NavigationWidget navigationWidget,
			final Provider<HttpContext> httpContextProvider,
			final RedirectUtil redirectUtil,
			final UrlUtil urlUtil,
			final GalleryService galleryService,
			final AuthorizationService authorizationService,
			final GalleryGuiLinkFactory galleryGuiLinkFactory) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil);
		this.galleryService = galleryService;
		this.logger = logger;
		this.urlUtil = urlUtil;
		this.galleryGuiLinkFactory = galleryGuiLinkFactory;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException,
			PermissionDeniedException, RedirectException, LoginRequiredException {
		try {
			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(TITLE));

			final String galleryId = request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_ID);
			final GalleryCollectionIdentifier galleryIdentifier = galleryService.createCollectionIdentifier(galleryId);

			final UlWidget ul = new UlWidget();
			for (final GalleryEntryIdentifier galleryEntryIdentifier : galleryService.getEntryIdentifiers(galleryIdentifier)) {
				final GalleryEntry entry = galleryService.getEntry(galleryEntryIdentifier);
				final ListWidget list = new ListWidget();
				list.add(new ImageWidget(galleryGuiLinkFactory.createImage(request, entry.getPreviewImageIdentifier())));
				list.add(galleryGuiLinkFactory.deleteEntry(request, galleryEntryIdentifier));
				ul.add(list);
			}
			widgets.add(ul);
			widgets.add(new LinkRelativWidget(urlUtil, request, "/" + GalleryGuiConstants.NAME + GalleryGuiConstants.URL_ENTRY_CREATE, new MapParameter().add(
					GalleryGuiConstants.PARAMETER_COLLECTION_ID, String.valueOf(galleryIdentifier)), "upload image"));
			return widgets;
		}
		catch (final GalleryServiceException e) {
			logger.debug(e.getClass().getName(), e);
			return new ExceptionWidget(e);
		}
	}
}