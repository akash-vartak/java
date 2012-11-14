package de.benjaminborbe.microblog.gui.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.microblog.api.MicroblogPostIdentifier;
import de.benjaminborbe.microblog.gui.MicroblogGuiConstants;
import de.benjaminborbe.tools.map.MapChain;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.link.LinkRelativWidget;

@Singleton
public class MicroblogGuiLinkFactory {

	private final UrlUtil urlUtil;

	@Inject
	public MicroblogGuiLinkFactory(final UrlUtil urlUtil) {
		this.urlUtil = urlUtil;
	}

	public Widget sendConversation(final HttpServletRequest request, final MicroblogPostIdentifier microblogPostIdentifier) throws MalformedURLException,
			UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + MicroblogGuiConstants.NAME + MicroblogGuiConstants.URL_CONVERSATION_SEND, new MapChain<String, String>().add(
				MicroblogGuiConstants.PARAMETER_POST_ID, String.valueOf(microblogPostIdentifier)), "send as conversation");
	}

	public Widget sendPost(final HttpServletRequest request, final MicroblogPostIdentifier microblogPostIdentifier) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + MicroblogGuiConstants.NAME + MicroblogGuiConstants.URL_POST_SEND, new MapChain<String, String>().add(
				MicroblogGuiConstants.PARAMETER_POST_ID, String.valueOf(microblogPostIdentifier)), "send as post");
	}

}