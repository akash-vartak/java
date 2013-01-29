package de.benjaminborbe.task.gui.servlet;

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
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.task.gui.util.TaskGuiLinkFactory;
import de.benjaminborbe.task.gui.util.TaskGuiUtil;
import de.benjaminborbe.task.gui.util.TaskGuiWidgetFactory;
import de.benjaminborbe.task.gui.widget.TaskFocusWidget;
import de.benjaminborbe.task.gui.widget.TaskGuiSwitchWidget;
import de.benjaminborbe.task.gui.widget.TaskNextWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.servlet.RedirectUtil;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.widget.ClearFloatWidget;

@Singleton
public class TaskGuiNextServlet extends TaskGuiWebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Tasks - Next";

	private final TaskFocusWidget taskFocusWidget;

	private final TaskNextWidget taskNextWidget;

	@Inject
	public TaskGuiNextServlet(
			final Logger logger,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final ParseUtil parseUtil,
			final AuthenticationService authenticationService,
			final NavigationWidget navigationWidget,
			final Provider<HttpContext> httpContextProvider,
			final RedirectUtil redirectUtil,
			final UrlUtil urlUtil,
			final AuthorizationService authorizationService,
			final TaskGuiLinkFactory taskGuiLinkFactory,
			final TaskGuiWidgetFactory taskGuiWidgetFactory,
			final TaskGuiUtil taskGuiUtil,
			final TaskGuiSwitchWidget taskGuiSwitchWidget,
			final TaskFocusWidget taskFocusWidget,
			final TaskNextWidget taskNextWidget,
			final CacheService cacheService) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, taskGuiUtil, cacheService);
		this.taskFocusWidget = taskFocusWidget;
		this.taskNextWidget = taskNextWidget;
	}

	@Override
	protected Widget createTaskContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException,
			PermissionDeniedException, RedirectException, LoginRequiredException {
		final ListWidget widgets = new ListWidget();
		widgets.add(taskFocusWidget);
		widgets.add(taskNextWidget);
		widgets.add(new ClearFloatWidget());
		return widgets;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

}
