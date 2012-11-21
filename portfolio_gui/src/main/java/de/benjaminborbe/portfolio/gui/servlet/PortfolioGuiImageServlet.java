package de.benjaminborbe.portfolio.gui.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.gallery.api.GalleryImage;
import de.benjaminborbe.gallery.api.GalleryImageIdentifier;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.portfolio.gui.PortfolioGuiConstants;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.servlet.WebsiteServlet;

@Singleton
public class PortfolioGuiImageServlet extends WebsiteServlet {

	private static final long serialVersionUID = -5013723680643328782L;

	private final GalleryService galleryService;

	private final Logger logger;

	private final AuthenticationService authenticationService;

	private final UrlUtil urlUtil;

	@Inject
	public PortfolioGuiImageServlet(
			final Logger logger,
			final GalleryService galleryService,
			final UrlUtil urlUtil,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final AuthenticationService authenticationService,
			final Provider<HttpContext> httpContextProvider) {
		super(logger, urlUtil, authenticationService, calendarUtil, timeZoneUtil, httpContextProvider);
		this.logger = logger;
		this.galleryService = galleryService;
		this.authenticationService = authenticationService;
		this.urlUtil = urlUtil;
	}

	@Override
	protected void doService(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws ServletException, IOException {
		try {
			// load image
			final String imageId = urlUtil.parseId(request, PortfolioGuiConstants.PARAMETER_IMAGE_ID);
			final GalleryImageIdentifier id = galleryService.createImageIdentifier(imageId);
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final GalleryImage image = galleryService.getImage(sessionIdentifier, id);
			logger.debug("loaded image " + image);

			// set header
			response.setContentType(image.getContentType());

			response.setDateHeader("Last-Modified", image.getModified().getTimeInMillis());
			response.setHeader("ETag", id.getId());

			// output image content
			final byte[] content = image.getContent();
			response.setContentLength(content.length);
			final ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(content);
		}
		catch (final Exception e) {
			response.setContentType("text/plain");
			final PrintWriter out = response.getWriter();
			out.println("fail");
			logger.debug(e.getClass().getSimpleName(), e);
		}
	}

	@Override
	protected boolean isLoginRequired() {
		return false;
	}

}