package de.benjaminborbe.poker.gui.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.api.ValidationError;
import de.benjaminborbe.api.ValidationErrorSimple;
import de.benjaminborbe.api.ValidationException;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.poker.api.PokerPlayer;
import de.benjaminborbe.poker.api.PokerPlayerDto;
import de.benjaminborbe.poker.api.PokerPlayerIdentifier;
import de.benjaminborbe.poker.api.PokerService;
import de.benjaminborbe.poker.api.PokerServiceException;
import de.benjaminborbe.poker.gui.PokerGuiConstants;
import de.benjaminborbe.poker.gui.util.PokerGuiLinkFactory;
import de.benjaminborbe.poker.gui.util.UserIdentifierComparator;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ComparatorUtil;
import de.benjaminborbe.tools.util.ParseException;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.validation.ValidationResultImpl;
import de.benjaminborbe.website.form.FormInputHiddenWidget;
import de.benjaminborbe.website.form.FormInputSubmitWidget;
import de.benjaminborbe.website.form.FormInputTextWidget;
import de.benjaminborbe.website.form.FormWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.servlet.RedirectUtil;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.widget.ValidationExceptionWidget;

@Singleton
public class PokerGuiPlayerUpdateServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Poker - Player - Update";

	private final PokerService pokerService;

	private final PokerGuiLinkFactory pokerGuiLinkFactory;

	private final AuthenticationService authenticationService;

	private final ComparatorUtil comparatorUtil;

	private final ParseUtil parseUtil;

	@Inject
	public PokerGuiPlayerUpdateServlet(
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
			final CacheService cacheService,
			final PokerService pokerService,
			final PokerGuiLinkFactory pokerGuiLinkFactory,
			final ComparatorUtil comparatorUtil) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.parseUtil = parseUtil;
		this.pokerService = pokerService;
		this.pokerGuiLinkFactory = pokerGuiLinkFactory;
		this.authenticationService = authenticationService;
		this.comparatorUtil = comparatorUtil;
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
			widgets.add(new H1Widget(getTitle()));

			final String id = request.getParameter(PokerGuiConstants.PARAMETER_PLAYER_ID);
			final String name = request.getParameter(PokerGuiConstants.PARAMETER_PLAYER_NAME);
			final String owners = request.getParameter(PokerGuiConstants.PARAMETER_PLAYER_OWNERS);
			final String amount = request.getParameter(PokerGuiConstants.PARAMETER_PLAYER_AMOUNT);

			final PokerPlayerIdentifier pokerPlayerIdentifier = pokerService.createPlayerIdentifier(id);
			final PokerPlayer player = pokerService.getPlayer(pokerPlayerIdentifier);

			final String referer = request.getParameter(PokerGuiConstants.PARAMETER_REFERER);
			if (name != null && owners != null && amount != null) {
				try {

					updatePlayer(pokerPlayerIdentifier, name, amount, buildUsers(owners));

					if (referer != null) {
						throw new RedirectException(referer);
					}
					else {
						throw new RedirectException(pokerGuiLinkFactory.playerListUrl(request));
					}
				}
				catch (final ValidationException e) {
					widgets.add("update player failed!");
					widgets.add(new ValidationExceptionWidget(e));
				}
			}

			final FormWidget form = new FormWidget();
			form.addFormInputWidget(new FormInputHiddenWidget(PokerGuiConstants.PARAMETER_REFERER).addDefaultValue(buildRefererUrl(request)));
			form.addFormInputWidget(new FormInputHiddenWidget(PokerGuiConstants.PARAMETER_PLAYER_ID).addValue(pokerPlayerIdentifier));
			form.addFormInputWidget(new FormInputTextWidget(PokerGuiConstants.PARAMETER_PLAYER_NAME).addLabel("Name:").addDefaultValue(player.getName()));
			form.addFormInputWidget(new FormInputTextWidget(PokerGuiConstants.PARAMETER_PLAYER_OWNERS).addLabel("Owners:").addDefaultValue(asString(player.getOwners())));
			form.addFormInputWidget(new FormInputTextWidget(PokerGuiConstants.PARAMETER_PLAYER_AMOUNT).addLabel("Credits:").addDefaultValue(player.getAmount()));
			form.addFormInputWidget(new FormInputSubmitWidget("update"));
			widgets.add(form);

			return widgets;
		}
		catch (final AuthenticationServiceException e) {
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
		catch (final PokerServiceException e) {
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
	}

	private String asString(final Collection<UserIdentifier> owners) {
		final StringBuffer sb = new StringBuffer();
		final List<UserIdentifier> list = comparatorUtil.sort(owners, new UserIdentifierComparator());
		for (final UserIdentifier user : list) {
			sb.append(user.getId());
			sb.append(" ");
		}
		return sb.toString();
	}

	private void updatePlayer(final PokerPlayerIdentifier pokerPlayerIdentifier, final String name, final String creditsString, final Collection<UserIdentifier> owners)
			throws PokerServiceException, ValidationException {

		final List<ValidationError> errors = new ArrayList<ValidationError>();
		long credits = 0;
		try {
			credits = parseUtil.parseLong(creditsString);
		}
		catch (final ParseException e) {
			errors.add(new ValidationErrorSimple("illegal credits"));
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(new ValidationResultImpl(errors));
		}
		else {
			final PokerPlayerDto playerDto = new PokerPlayerDto();
			playerDto.setId(pokerPlayerIdentifier);
			playerDto.setName(name);
			playerDto.setAmount(credits);
			playerDto.setOwners(owners);
			pokerService.updatePlayer(playerDto);
		}

	}

	private Collection<UserIdentifier> buildUsers(final String owners) throws AuthenticationServiceException {
		final List<UserIdentifier> users = new ArrayList<>();
		for (final String owner : owners.split("[^a-z]")) {
			final UserIdentifier userIdentifier = authenticationService.createUserIdentifier(owner);
			if (authenticationService.existsUser(userIdentifier)) {
				users.add(userIdentifier);
			}
		}
		return users;
	}

	@Override
	protected void doCheckPermission(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws ServletException, IOException,
			PermissionDeniedException, LoginRequiredException {
		try {
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			pokerService.expectPokerAdminRole(sessionIdentifier);
		}
		catch (final AuthenticationServiceException e) {
			throw new PermissionDeniedException(e);
		}
		catch (final PokerServiceException e) {
			throw new PermissionDeniedException(e);
		}
	}

	@Override
	public boolean isAdminRequired() {
		return false;
	}
}
