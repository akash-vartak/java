package de.benjaminborbe.website.link;

import java.net.URL;

import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.tools.html.Target;
import de.benjaminborbe.website.util.StringWidget;
import de.benjaminborbe.website.util.TagWidget;

public class LinkWidget extends TagWidget {

	public LinkWidget(final URL url, final Widget contentWidget) {
		super("a", contentWidget);
		addAttribute("href", url.toExternalForm());
	}

	public LinkWidget(final URL url, final String content) {
		this(url, new StringWidget(content));
	}

	public LinkWidget addTarget(final Target target) {
		addAttribute("target", String.valueOf(target));
		return this;
	}

	public LinkWidget addConfirm(final String message) {
		addAttribute("onclick", "if (confirm('" + message + "')) {return true;} else { return false; }");
		return this;
	}

}
